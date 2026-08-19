package br.edu.ufape.poo.escola.negocio.servico;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.ufape.poo.escola.dados.InterfaceRepositorioDisciplina;
import br.edu.ufape.poo.escola.dados.InterfaceRepositorioProfessor;
import br.edu.ufape.poo.escola.dados.InterfaceRepositorioTurma;
import br.edu.ufape.poo.escola.negocio.basica.Disciplina;
import br.edu.ufape.poo.escola.negocio.basica.Professor;
import br.edu.ufape.poo.escola.negocio.basica.Turma;
import br.edu.ufape.poo.escola.negocio.excecoes.EntidadeNaoEncontradaException;
import br.edu.ufape.poo.escola.negocio.excecoes.RegrasNegocioException;

@Service
public class ServicoDisciplina {

    private final InterfaceRepositorioDisciplina repositorioDisciplina;
    private final InterfaceRepositorioTurma repositorioTurma;
    private final InterfaceRepositorioProfessor repositorioProfessor;

    public ServicoDisciplina(InterfaceRepositorioDisciplina repositorioDisciplina,
            InterfaceRepositorioTurma repositorioTurma, InterfaceRepositorioProfessor repositorioProfessor) {
        this.repositorioDisciplina = repositorioDisciplina;
        this.repositorioTurma = repositorioTurma;
        this.repositorioProfessor = repositorioProfessor;
    }

    public Disciplina salvarDisciplina(Disciplina disciplina) {
        if (disciplina == null) {
            throw new RegrasNegocioException("A disciplina não pode ser nula.");
        }
        if (disciplina.getNome() == null || disciplina.getNome().isBlank()) {
            throw new RegrasNegocioException("O nome da disciplina é obrigatório.");
        }
        if (repositorioDisciplina.findByNome(disciplina.getNome()) != null) {
            throw new RegrasNegocioException("Já existe uma disciplina com este nome.");
        }
        return repositorioDisciplina.save(disciplina);
    }

    public Disciplina buscarDisciplinaPorNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new RegrasNegocioException("O nome da disciplina é obrigatório.");
        }
        Disciplina disciplina = repositorioDisciplina.findByNome(nome);
        if (disciplina == null) {
            throw new EntidadeNaoEncontradaException("Disciplina não encontrada: " + nome);
        }
        return disciplina;
    }

    public List<Disciplina> buscarTodasDisciplinas() {
        return repositorioDisciplina.findAll();
    }

    public Turma salvarTurma(Turma turma) {
        if (turma == null) {
            throw new RegrasNegocioException("A turma não pode ser nula.");
        }
        if (turma.getNome() == null || turma.getNome().isBlank()) {
            throw new RegrasNegocioException("O nome da turma é obrigatório.");
        }
        if (turma.getAno() == null || turma.getAno() <= 0) {
            throw new RegrasNegocioException("O ano da turma é obrigatório e deve ser maior que zero.");
        }
        return repositorioTurma.save(turma);
    }

    public List<Turma> buscarTurmasPorDisciplina(String nomeDisciplina) {
        if (nomeDisciplina == null || nomeDisciplina.isBlank()) {
            throw new RegrasNegocioException("O nome da disciplina é obrigatório.");
        }
        Disciplina disciplina = repositorioDisciplina.findByNome(nomeDisciplina);
        if (disciplina == null) {
            throw new EntidadeNaoEncontradaException("Disciplina não encontrada: " + nomeDisciplina);
        }
        return repositorioTurma.findAll().stream()
                .filter(turma -> turma.getDisciplina() != null && turma.getDisciplina().getNome().equalsIgnoreCase(nomeDisciplina))
                .toList();
    }

    public List<Professor> buscarProfessoresPorEspecialidade(String especialidade) {
        if (especialidade == null || especialidade.isBlank()) {
            throw new RegrasNegocioException("A especialidade é obrigatória.");
        }
        return repositorioProfessor.findByEspecialidadeIgnoreCase(especialidade);
    }
}
