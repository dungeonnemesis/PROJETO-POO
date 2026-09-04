package br.edu.ufape.poo.escola.negocio.servico;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.edu.ufape.poo.escola.comunicacao.dto.ProfessorRequest;
import br.edu.ufape.poo.escola.dados.InterfaceRepositorioProfessor;
import br.edu.ufape.poo.escola.negocio.basica.Professor;
import br.edu.ufape.poo.escola.negocio.excecao.RecursoNaoEncontradoException;

@Service
public class ProfessorService {
	private final InterfaceRepositorioProfessor repositorio;
	public ProfessorService(InterfaceRepositorioProfessor repositorio) { this.repositorio = repositorio; }
	@Transactional(readOnly = true) public List<Professor> listar() { return repositorio.findAll(); }
	@Transactional(readOnly = true) public Professor buscar(Long id) { return repositorio.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Professor", id)); }
	@Transactional public Professor criar(ProfessorRequest dto) { return repositorio.save(new Professor(dto.nome(), dto.cpf(), dto.email(), dto.especialidade())); }
	@Transactional public Professor atualizar(Long id, ProfessorRequest dto) {
		Professor p = buscar(id); p.setNome(dto.nome()); p.setCpf(dto.cpf()); p.setEmail(dto.email()); p.setEspecialidade(dto.especialidade()); return repositorio.save(p);
	}
	@Transactional public void excluir(Long id) { repositorio.delete(buscar(id)); }
}
