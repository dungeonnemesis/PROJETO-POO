package br.edu.ufape.poo.escola.negocio.fachada;

import java.util.List;

import org.springframework.stereotype.Component;

import br.edu.ufape.poo.escola.negocio.basica.Aluno;
import br.edu.ufape.poo.escola.negocio.basica.Disciplina;
import br.edu.ufape.poo.escola.negocio.basica.Matricula;
import br.edu.ufape.poo.escola.negocio.basica.Professor;
import br.edu.ufape.poo.escola.negocio.basica.Turma;
import br.edu.ufape.poo.escola.negocio.servico.ServicoAluno;
import br.edu.ufape.poo.escola.negocio.servico.ServicoDisciplina;

@Component
public class EscolaFacade {

    private final ServicoAluno servicoAluno;
    private final ServicoDisciplina servicoDisciplina;

    public EscolaFacade(ServicoAluno servicoAluno, ServicoDisciplina servicoDisciplina) {
        this.servicoAluno = servicoAluno;
        this.servicoDisciplina = servicoDisciplina;
    }

    public Aluno criarAluno(Aluno aluno) {
        return servicoAluno.salvarAluno(aluno);
    }

    public Aluno buscarAlunoPorMatricula(String matricula) {
        return servicoAluno.buscarAlunoPorMatricula(matricula);
    }

    public List<Aluno> buscarTodosAlunos() {
        return servicoAluno.buscarTodosAlunos();
    }

    public Disciplina criarDisciplina(Disciplina disciplina) {
        return servicoDisciplina.salvarDisciplina(disciplina);
    }

    public Disciplina buscarDisciplinaPorNome(String nome) {
        return servicoDisciplina.buscarDisciplinaPorNome(nome);
    }

    public List<Disciplina> buscarTodasDisciplinas() {
        return servicoDisciplina.buscarTodasDisciplinas();
    }

    public Turma criarTurma(Turma turma) {
        return servicoDisciplina.salvarTurma(turma);
    }

    public List<Turma> buscarTurmasPorDisciplina(String nomeDisciplina) {
        return servicoDisciplina.buscarTurmasPorDisciplina(nomeDisciplina);
    }

    public Matricula matricularAluno(Aluno aluno, Turma turma) {
        return servicoAluno.matricularAluno(aluno, turma);
    }

    public List<Professor> buscarProfessoresPorEspecialidade(String especialidade) {
        return servicoDisciplina.buscarProfessoresPorEspecialidade(especialidade);
    }
}
