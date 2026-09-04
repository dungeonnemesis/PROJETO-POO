package br.edu.ufape.poo.escola.negocio.servico;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ufape.poo.escola.comunicacao.dto.AlunoRequest;
import br.edu.ufape.poo.escola.dados.InterfaceRepositorioAluno;
import br.edu.ufape.poo.escola.negocio.basica.Aluno;
import br.edu.ufape.poo.escola.negocio.excecao.RecursoNaoEncontradoException;

@Service
public class AlunoService {
	private final InterfaceRepositorioAluno repositorio;

	public AlunoService(InterfaceRepositorioAluno repositorio) { this.repositorio = repositorio; }

	@Transactional(readOnly = true)
	public List<Aluno> listar() { return repositorio.findAll(); }

	@Transactional(readOnly = true)
	public Aluno buscar(Long id) { return repositorio.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Aluno", id)); }

	@Transactional
	public Aluno criar(AlunoRequest dto) { return repositorio.save(new Aluno(dto.nome(), dto.cpf(), dto.email(), dto.matricula())); }

	@Transactional
	public Aluno atualizar(Long id, AlunoRequest dto) {
		Aluno aluno = buscar(id);
		aluno.setNome(dto.nome()); aluno.setCpf(dto.cpf()); aluno.setEmail(dto.email()); aluno.setMatricula(dto.matricula());
		return repositorio.save(aluno);
	}

	@Transactional
	public void excluir(Long id) { repositorio.delete(buscar(id)); }
}
