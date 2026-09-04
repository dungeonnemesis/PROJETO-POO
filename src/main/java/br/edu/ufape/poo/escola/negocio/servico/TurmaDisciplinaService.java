package br.edu.ufape.poo.escola.negocio.servico;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.edu.ufape.poo.escola.comunicacao.dto.TurmaDisciplinaRequest;
import br.edu.ufape.poo.escola.dados.InterfaceRepositorioTurmaDisciplina;
import br.edu.ufape.poo.escola.negocio.basica.TurmaDisciplina;
import br.edu.ufape.poo.escola.negocio.excecao.RecursoNaoEncontradoException;

@Service
public class TurmaDisciplinaService {
	private final InterfaceRepositorioTurmaDisciplina repositorio;
	private final TurmaService turmaService;
	private final DisciplinaService disciplinaService;
	private final ProfessorService professorService;

	public TurmaDisciplinaService(InterfaceRepositorioTurmaDisciplina repositorio, TurmaService turmaService,
			DisciplinaService disciplinaService, ProfessorService professorService) {
		this.repositorio = repositorio; this.turmaService = turmaService;
		this.disciplinaService = disciplinaService; this.professorService = professorService;
	}

	@Transactional(readOnly = true)
	public List<TurmaDisciplina> listar(Long turmaId) { turmaService.buscar(turmaId); return repositorio.findByTurmaId(turmaId); }

	@Transactional
	public TurmaDisciplina adicionar(Long turmaId, TurmaDisciplinaRequest dto) {
		var turma = turmaService.buscar(turmaId);
		var disciplina = disciplinaService.buscar(dto.disciplinaId());
		var professor = professorService.buscar(dto.professorId());
		if (repositorio.existsByTurmaIdAndDisciplinaId(turmaId, dto.disciplinaId()))
			throw new IllegalStateException("A disciplina ja foi adicionada a esta turma");
		return repositorio.save(new TurmaDisciplina(turma, disciplina, professor));
	}

	@Transactional
	public void remover(Long turmaId, Long gradeId) {
		TurmaDisciplina grade = repositorio.findById(gradeId)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Grade da turma", gradeId));
		if (!grade.getTurma().getId().equals(turmaId)) throw new RecursoNaoEncontradoException("Grade da turma", gradeId);
		repositorio.delete(grade);
	}
}
