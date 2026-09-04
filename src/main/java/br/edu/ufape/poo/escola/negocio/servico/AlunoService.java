package br.edu.ufape.poo.escola.negocio.servico;

import java.util.List;
import java.time.Year;

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
	public Aluno criar(AlunoRequest dto) {
		return repositorio.save(new Aluno(dto.nome(), dto.cpf(), dto.email(), gerarMatricula()));
	}

	@Transactional
	public Aluno atualizar(Long id, AlunoRequest dto) {
		Aluno aluno = buscar(id);
		aluno.setNome(dto.nome()); aluno.setCpf(dto.cpf()); aluno.setEmail(dto.email());
		return repositorio.save(aluno);
	}

	@Transactional
	public void excluir(Long id) { repositorio.delete(buscar(id)); }

	private String gerarMatricula() {
		long sequencia = repositorio.count() + 1;
		String matricula;
		do {
			matricula = Year.now().getValue() + String.format("%03d", sequencia++);
		} while (repositorio.findByMatricula(matricula) != null);
		return matricula;
	}
}
