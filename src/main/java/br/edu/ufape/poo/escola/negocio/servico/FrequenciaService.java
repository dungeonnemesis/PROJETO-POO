package br.edu.ufape.poo.escola.negocio.servico;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ufape.poo.escola.comunicacao.dto.FrequenciaLoteRequest;
import br.edu.ufape.poo.escola.comunicacao.dto.FrequenciaRequest;
import br.edu.ufape.poo.escola.dados.InterfaceRepositorioFrequencia;
import br.edu.ufape.poo.escola.dados.InterfaceRepositorioTurmaDisciplina;
import br.edu.ufape.poo.escola.negocio.basica.Frequencia;
import br.edu.ufape.poo.escola.negocio.basica.Matricula;
import br.edu.ufape.poo.escola.negocio.basica.StatusFrequencia;
import br.edu.ufape.poo.escola.negocio.basica.TurmaDisciplina;
import br.edu.ufape.poo.escola.negocio.excecao.RecursoNaoEncontradoException;
import br.edu.ufape.poo.escola.negocio.excecoes.FrequenciaJaRegistradaException;

@Service
public class FrequenciaService {

	private final InterfaceRepositorioFrequencia repositorio;
	private final MatriculaService matriculaService;
	private final InterfaceRepositorioTurmaDisciplina gradeRepositorio;

	public FrequenciaService(InterfaceRepositorioFrequencia repositorio, MatriculaService matriculaService,
			InterfaceRepositorioTurmaDisciplina gradeRepositorio) {
		this.repositorio = repositorio;
		this.matriculaService = matriculaService;
		this.gradeRepositorio = gradeRepositorio;
	}

	@Transactional(readOnly = true)
	public List<Frequencia> listar() {
		return repositorio.findAll();
	}

	@Transactional(readOnly = true)
	public Frequencia buscar(Long id) {
		return repositorio.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Frequencia", id));
	}

	@Transactional(readOnly = true)
	public List<Frequencia> buscarPorMatricula(Long matriculaId) {
		return repositorio.findByMatriculaId(matriculaId);
	}

	/**
	 * Percentual de presença de uma matrícula, considerando PRESENTE e
	 * JUSTIFICADA como frequência computada. Regra de negócio pensada para
	 * ser coberta pelo teste unitário.
	 */
	@Transactional(readOnly = true)
	public double calcularPercentualPresenca(Long matriculaId) {
		List<Frequencia> registros = repositorio.findByMatriculaId(matriculaId);
		if (registros.isEmpty()) {
			return 0.0;
		}
		long presentes = registros.stream()
				.filter(f -> f.getStatus() == StatusFrequencia.PRESENTE || f.getStatus() == StatusFrequencia.JUSTIFICADA)
				.count();
		return (presentes * 100.0) / registros.size();
	}

	@Transactional
	public Frequencia criar(FrequenciaRequest dto) {
		Matricula matricula = matriculaService.buscar(dto.matriculaId());
		TurmaDisciplina grade = buscarGrade(dto.gradeId());
		validarGrade(matricula, grade);
		validarDuplicidade(matricula, grade, dto.data());
		return repositorio.save(new Frequencia(dto.data(), dto.status(), matricula, grade));
	}

	@Transactional
	public Frequencia atualizar(Long id, FrequenciaRequest dto) {
		Frequencia frequencia = buscar(id);
		Matricula matricula = matriculaService.buscar(dto.matriculaId());
		TurmaDisciplina grade = buscarGrade(dto.gradeId());
		validarGrade(matricula, grade);
		frequencia.setData(dto.data());
		frequencia.setStatus(dto.status());
		frequencia.setMatricula(matricula);
		frequencia.setGrade(grade);
		return repositorio.save(frequencia);
	}

	@Transactional
	public void excluir(Long id) {
		repositorio.delete(buscar(id));
	}

	/**
	 * Lança frequência para vários alunos de uma turma, na mesma data e
	 * disciplina, em uma única chamada — espelha o endpoint de lote já
	 * existente para Nota.
	 */
	@Transactional
	public List<Frequencia> criarEmLote(FrequenciaLoteRequest dto) {
		TurmaDisciplina grade = buscarGrade(dto.gradeId());
		if (!grade.getTurma().getId().equals(dto.turmaId())) {
			throw new IllegalArgumentException("A disciplina nao pertence a turma selecionada");
		}
		List<Frequencia> registros = dto.registros().stream().map(item -> {
			Matricula matricula = matriculaService.buscar(item.matriculaId());
			validarGrade(matricula, grade);
			validarDuplicidade(matricula, grade, dto.data());
			return new Frequencia(dto.data(), item.status(), matricula, grade);
		}).toList();
		return repositorio.saveAll(registros);
	}

	private TurmaDisciplina buscarGrade(Long gradeId) {
		return gradeRepositorio.findById(gradeId)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Disciplina da turma", gradeId));
	}

	private void validarGrade(Matricula matricula, TurmaDisciplina grade) {
		if (!grade.getTurma().getId().equals(matricula.getTurma().getId())) {
			throw new IllegalArgumentException("A disciplina nao pertence a turma da matricula");
		}
	}

	private void validarDuplicidade(Matricula matricula, TurmaDisciplina grade, LocalDate data) {
		if (repositorio.existsByMatriculaIdAndGradeIdAndData(matricula.getId(), grade.getId(), data)) {
			throw new FrequenciaJaRegistradaException(
					"Ja existe frequencia registrada para esta matricula, disciplina e data.");
		}
	}
}
