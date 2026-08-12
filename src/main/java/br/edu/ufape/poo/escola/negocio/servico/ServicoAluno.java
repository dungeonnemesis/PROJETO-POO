package br.edu.ufape.poo.escola.negocio.servico;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ufape.poo.escola.dados.InterfaceRepositorioAluno;
import br.edu.ufape.poo.escola.dados.InterfaceRepositorioMatricula;
import br.edu.ufape.poo.escola.negocio.basica.Aluno;
import br.edu.ufape.poo.escola.negocio.basica.Matricula;
import br.edu.ufape.poo.escola.negocio.basica.Turma;
import br.edu.ufape.poo.escola.negocio.excecoes.EntidadeNaoEncontradaException;
import br.edu.ufape.poo.escola.negocio.excecoes.RegrasNegocioException;

@Service
public class ServicoAluno {

    private final InterfaceRepositorioAluno repositorioAluno;
    private final InterfaceRepositorioMatricula repositorioMatricula;

    public ServicoAluno(InterfaceRepositorioAluno repositorioAluno, InterfaceRepositorioMatricula repositorioMatricula) {
        this.repositorioAluno = repositorioAluno;
        this.repositorioMatricula = repositorioMatricula;
    }

    public Aluno salvarAluno(Aluno aluno) {
        if (aluno == null) {
            throw new RegrasNegocioException("O aluno não pode ser nulo.");
        }
        if (aluno.getMatricula() == null || aluno.getMatricula().isBlank()) {
            throw new RegrasNegocioException("A matrícula do aluno é obrigatória.");
        }
        if (repositorioAluno.findByMatricula(aluno.getMatricula()) != null) {
            throw new RegrasNegocioException("Já existe um aluno com esta matrícula.");
        }
        return repositorioAluno.save(aluno);
    }

    public Aluno buscarAlunoPorMatricula(String matricula) {
        if (matricula == null || matricula.isBlank()) {
            throw new RegrasNegocioException("A matrícula é obrigatória.");
        }
        Aluno aluno = repositorioAluno.findByMatricula(matricula);
        if (aluno == null) {
            throw new EntidadeNaoEncontradaException("Aluno não encontrado para a matrícula: " + matricula);
        }
        return aluno;
    }

    public List<Aluno> buscarTodosAlunos() {
        return repositorioAluno.findAll();
    }

    @Transactional
    public Matricula matricularAluno(Aluno aluno, Turma turma) {
        if (aluno == null) {
            throw new RegrasNegocioException("Aluno não pode ser nulo.");
        }
        if (turma == null) {
            throw new RegrasNegocioException("Turma não pode ser nula.");
        }
        if (aluno.getId() == null || !repositorioAluno.existsById(aluno.getId())) {
            throw new EntidadeNaoEncontradaException("Aluno não cadastrado: " + aluno.getNome());
        }
        Matricula matricula = new Matricula(aluno, turma);
        return repositorioMatricula.save(matricula);
    }
}
