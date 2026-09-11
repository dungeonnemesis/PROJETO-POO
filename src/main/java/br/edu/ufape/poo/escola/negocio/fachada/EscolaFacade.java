package br.edu.ufape.poo.escola.negocio.fachada;

import java.util.List;

import org.springframework.stereotype.Component;

import br.edu.ufape.poo.escola.comunicacao.dto.JustificativaRequest;
import br.edu.ufape.poo.escola.negocio.basica.Aluno;
import br.edu.ufape.poo.escola.negocio.basica.Disciplina;
import br.edu.ufape.poo.escola.negocio.basica.Frequencia;
import br.edu.ufape.poo.escola.negocio.basica.Justificativa;
import br.edu.ufape.poo.escola.negocio.basica.Matricula;
import br.edu.ufape.poo.escola.negocio.basica.Professor;
import br.edu.ufape.poo.escola.negocio.basica.StatusFrequencia;
import br.edu.ufape.poo.escola.negocio.basica.Turma;
import br.edu.ufape.poo.escola.negocio.excecoes.JustificativaInvalidaException;
import br.edu.ufape.poo.escola.negocio.servico.FrequenciaService;
import br.edu.ufape.poo.escola.negocio.servico.JustificativaService;
import br.edu.ufape.poo.escola.negocio.servico.ServicoAluno;
import br.edu.ufape.poo.escola.negocio.servico.ServicoDisciplina;

@Component
public class EscolaFacade {

    private final ServicoAluno servicoAluno;
    private final ServicoDisciplina servicoDisciplina;
    private final FrequenciaService frequenciaService;
    private final JustificativaService justificativaService;

    public EscolaFacade(ServicoAluno servicoAluno, ServicoDisciplina servicoDisciplina,
            FrequenciaService frequenciaService, JustificativaService justificativaService) {
        this.servicoAluno = servicoAluno;
        this.servicoDisciplina = servicoDisciplina;
        this.frequenciaService = frequenciaService;
        this.justificativaService = justificativaService;
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

    /**
     * Registra a justificativa de uma falta. A regra de que só é possível
     * justificar uma frequência cujo status seja AUSENTE mora aqui, na
     * fachada — é o ponto de entrada que orquestra a consulta à
     * Frequencia (via FrequenciaService) e a criação da Justificativa
     * (via JustificativaService), garantindo a regra antes de delegar.
     */
    public Justificativa registrarJustificativaDeFalta(Long frequenciaId, String motivo) {
        Frequencia frequencia = frequenciaService.buscar(frequenciaId);
        if (frequencia.getStatus() != StatusFrequencia.AUSENTE) {
            throw new JustificativaInvalidaException(
                    "So e possivel justificar uma frequencia registrada como AUSENTE.");
        }
        return justificativaService.criar(new JustificativaRequest(frequenciaId, motivo));
    }
}
