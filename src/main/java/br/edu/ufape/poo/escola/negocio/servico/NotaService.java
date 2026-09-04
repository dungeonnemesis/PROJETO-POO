package br.edu.ufape.poo.escola.negocio.servico;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.edu.ufape.poo.escola.comunicacao.dto.NotaRequest;
import br.edu.ufape.poo.escola.comunicacao.dto.NotaLoteRequest;
import br.edu.ufape.poo.escola.dados.InterfaceRepositorioNota;
import br.edu.ufape.poo.escola.dados.InterfaceRepositorioTurmaDisciplina;
import br.edu.ufape.poo.escola.negocio.basica.Nota;
import br.edu.ufape.poo.escola.negocio.excecao.RecursoNaoEncontradoException;

@Service
public class NotaService {
	private final InterfaceRepositorioNota repositorio;
	private final MatriculaService matriculaService;
	private final InterfaceRepositorioTurmaDisciplina gradeRepositorio;
	public NotaService(InterfaceRepositorioNota repositorio, MatriculaService matriculaService, InterfaceRepositorioTurmaDisciplina gradeRepositorio) { this.repositorio = repositorio; this.matriculaService = matriculaService; this.gradeRepositorio = gradeRepositorio; }
	@Transactional(readOnly = true) public List<Nota> listar() { return repositorio.findAll(); }
	@Transactional(readOnly = true) public Nota buscar(Long id) { return repositorio.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Nota", id)); }
	@Transactional public Nota criar(NotaRequest dto) { var matricula = matriculaService.buscar(dto.matriculaId()); var grade = gradeRepositorio.findById(dto.gradeId()).orElseThrow(() -> new RecursoNaoEncontradoException("Disciplina da turma", dto.gradeId())); validarGrade(matricula, grade); return repositorio.save(new Nota(dto.valor(), matricula, grade)); }
	@Transactional public Nota atualizar(Long id, NotaRequest dto) { Nota n = buscar(id); var matricula = matriculaService.buscar(dto.matriculaId()); var grade = gradeRepositorio.findById(dto.gradeId()).orElseThrow(() -> new RecursoNaoEncontradoException("Disciplina da turma", dto.gradeId())); validarGrade(matricula, grade); n.setValor(dto.valor()); n.setMatricula(matricula); n.setGrade(grade); return repositorio.save(n); }
	@Transactional public void excluir(Long id) { repositorio.delete(buscar(id)); }
	@Transactional public List<Nota> criarEmLote(NotaLoteRequest dto) {
		var turma = matriculaService.buscar(dto.notas().get(0).matriculaId()).getTurma();
		var grade = gradeRepositorio.findById(dto.gradeId()).orElseThrow(() -> new RecursoNaoEncontradoException("Disciplina da turma", dto.gradeId()));
		if (!turma.getId().equals(dto.turmaId()) || !grade.getTurma().getId().equals(dto.turmaId())) throw new IllegalArgumentException("A disciplina nao pertence a turma selecionada");
		var notas = dto.notas().stream().map(item -> {
			var matricula = matriculaService.buscar(item.matriculaId());
			validarGrade(matricula, grade);
			if (item.valor() < 0 || item.valor() > 10) throw new IllegalArgumentException("A nota deve estar entre zero e dez");
			return new Nota(item.valor(), matricula, grade);
		}).toList();
		return repositorio.saveAll(notas);
	}
	private void validarGrade(br.edu.ufape.poo.escola.negocio.basica.Matricula matricula, br.edu.ufape.poo.escola.negocio.basica.TurmaDisciplina grade) { if (!grade.getTurma().getId().equals(matricula.getTurma().getId())) throw new IllegalArgumentException("A disciplina nao pertence a turma da matricula"); }
}
