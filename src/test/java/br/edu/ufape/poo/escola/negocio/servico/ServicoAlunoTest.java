package br.edu.ufape.poo.escola.negocio.servico;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ufape.poo.escola.negocio.basica.Aluno;
import br.edu.ufape.poo.escola.negocio.basica.Matricula;
import br.edu.ufape.poo.escola.negocio.basica.Turma;
import br.edu.ufape.poo.escola.negocio.excecoes.EntidadeNaoEncontradaException;
import br.edu.ufape.poo.escola.negocio.excecoes.RegrasNegocioException;

@SpringBootTest
@Transactional
public class ServicoAlunoTest {

    @Autowired
    private ServicoAluno servicoAluno;

    @Test
    public void testarSalvarAlunoComSucesso() {
        Aluno aluno = new Aluno("João Silva", "12345678901", "joao@email.com", "2026001");
        Aluno salvo = servicoAluno.salvarAluno(aluno);

        assertNotNull(salvo.getId());
        assertEquals("2026001", salvo.getMatricula());
    }

    @Test
    public void testarSalvarAlunoSemMatriculaDeveFalhar() {
        Aluno aluno = new Aluno("João Silva", "12345678901", "joao@email.com", "");

        assertThrows(RegrasNegocioException.class, () -> servicoAluno.salvarAluno(aluno));
    }

    @Test
    public void testarBuscarAlunoPorMatriculaInexistenteDeveFalhar() {
        assertThrows(EntidadeNaoEncontradaException.class, () -> servicoAluno.buscarAlunoPorMatricula("nao-existe"));
    }

    @Test
    public void testarMatricularAlunoSemCadastroDeveFalhar() {
        Aluno aluno = new Aluno("João Silva", "12345678901", "joao@email.com", "2026001");
        Turma turma = new Turma("Turma A", 2026, new br.edu.ufape.poo.escola.negocio.basica.Disciplina("Matemática", 80));

        assertThrows(EntidadeNaoEncontradaException.class, () -> servicoAluno.matricularAluno(aluno, turma));
    }

    @Test
    public void testarBuscarTodosAlunosDeveRetornarLista() {
        servicoAluno.salvarAluno(new Aluno("Maria Souza", "98765432100", "maria@email.com", "2026002"));

        assertNotNull(servicoAluno.buscarTodosAlunos());
    }
}
