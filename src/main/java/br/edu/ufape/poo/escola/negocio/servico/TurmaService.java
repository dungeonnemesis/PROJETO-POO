package br.edu.ufape.poo.escola.negocio.servico;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.edu.ufape.poo.escola.comunicacao.dto.TurmaRequest;
import br.edu.ufape.poo.escola.dados.InterfaceRepositorioTurma;
import br.edu.ufape.poo.escola.negocio.basica.Turma;
import br.edu.ufape.poo.escola.negocio.excecao.RecursoNaoEncontradoException;

@Service
public class TurmaService {
	private final InterfaceRepositorioTurma repositorio;
	public TurmaService(InterfaceRepositorioTurma repositorio, DisciplinaService disciplinaService, ProfessorService professorService) { this.repositorio = repositorio; }
	@Transactional(readOnly = true) public List<Turma> listar() { return repositorio.findAll(); }
	@Transactional(readOnly = true) public Turma buscar(Long id) { return repositorio.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Turma", id)); }
	@Transactional public Turma criar(TurmaRequest dto) { return repositorio.save(new Turma(dto.nome(), dto.ano(), dto.turno())); }
	@Transactional public Turma atualizar(Long id, TurmaRequest dto) {
		Turma t = buscar(id); t.setNome(dto.nome()); t.setAno(dto.ano()); t.setTurno(dto.turno()); return repositorio.save(t);
	}
	@Transactional public void excluir(Long id) { repositorio.delete(buscar(id)); }
}
