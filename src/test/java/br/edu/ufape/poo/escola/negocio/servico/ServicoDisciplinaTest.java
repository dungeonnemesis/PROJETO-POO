package br.edu.ufape.poo.escola.negocio.servico;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ufape.poo.escola.negocio.basica.Disciplina;
import br.edu.ufape.poo.escola.negocio.basica.Professor;
import br.edu.ufape.poo.escola.negocio.basica.Turma;
import br.edu.ufape.poo.escola.negocio.excecoes.EntidadeNaoEncontradaException;
import br.edu.ufape.poo.escola.negocio.excecoes.RegrasNegocioException;

@SpringBootTest
@Transactional
public class ServicoDisciplinaTest {

    @Autowired
    private ServicoDisciplina servicoDisciplina;

    @Test
    public void testarSalvarDisciplinaComSucesso() {
        Disciplina disciplina = new Disciplina("História", 60);
        Disciplina salva = servicoDisciplina.salvarDisciplina(disciplina);

        assertNotNull(salva.getId());
        assertEquals("História", salva.getNome());
    }

    @Test
    public void testarSalvarDisciplinaSemNomeDeveFalhar() {
        Disciplina disciplina = new Disciplina("", 60);

        assertThrows(RegrasNegocioException.class, () -> servicoDisciplina.salvarDisciplina(disciplina));
    }

    @Test
    public void testarBuscarDisciplinaPorNomeInexistenteDeveFalhar() {
        assertThrows(EntidadeNaoEncontradaException.class, () -> servicoDisciplina.buscarDisciplinaPorNome("NãoExiste"));
    }

    @Test
    public void testarSalvarTurmaComSucesso() {
        Disciplina disciplina = new Disciplina("Química", 80);
        Turma turma = new Turma("Turma B", 2026, disciplina);

        Turma salva = servicoDisciplina.salvarTurma(turma);

        assertNotNull(salva.getId());
        assertEquals("Turma B", salva.getNome());
    }

    @Test
    public void testarBuscarProfessoresPorEspecialidade() {
        assertNotNull(servicoDisciplina.buscarProfessoresPorEspecialidade("Matemática"));
    }
}
