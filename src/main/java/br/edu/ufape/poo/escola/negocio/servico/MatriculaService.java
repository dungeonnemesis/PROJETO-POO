package br.edu.ufape.poo.escola.negocio.servico;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.edu.ufape.poo.escola.comunicacao.dto.MatriculaRequest;
import br.edu.ufape.poo.escola.dados.InterfaceRepositorioMatricula;
import br.edu.ufape.poo.escola.negocio.basica.Matricula;
import br.edu.ufape.poo.escola.negocio.excecao.RecursoNaoEncontradoException;

@Service
public class MatriculaService {
	private final InterfaceRepositorioMatricula repositorio;
	private final AlunoService alunoService;
	private final TurmaService turmaService;

	public MatriculaService(InterfaceRepositorioMatricula repositorio, AlunoService alunoService, TurmaService turmaService) {
		this.repositorio = repositorio; this.alunoService = alunoService; this.turmaService = turmaService;
	}
	@Transactional(readOnly = true) public List<Matricula> listar() { return repositorio.findAll(); }
	@Transactional(readOnly = true) public Matricula buscar(Long id) { return repositorio.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Matricula", id)); }
	@Transactional public Matricula criar(MatriculaRequest dto) {
		if (repositorio.existsByAlunoIdAndTurmaId(dto.alunoId(), dto.turmaId())) throw new DataIntegrityViolationException("Aluno ja matriculado na turma");
		return repositorio.save(new Matricula(dto.data(), dto.status(), alunoService.buscar(dto.alunoId()), turmaService.buscar(dto.turmaId())));
	}
	@Transactional public Matricula atualizar(Long id, MatriculaRequest dto) {
		Matricula m = buscar(id); m.setData(dto.data()); m.setStatus(dto.status()); m.setAluno(alunoService.buscar(dto.alunoId())); m.setTurma(turmaService.buscar(dto.turmaId())); return repositorio.save(m);
	}
	@Transactional public void excluir(Long id) { repositorio.delete(buscar(id)); }
}
