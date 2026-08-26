package br.edu.ufape.poo.escola.dados;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ufape.poo.escola.negocio.basica.Aluno;
import br.edu.ufape.poo.escola.negocio.basica.Disciplina;
import br.edu.ufape.poo.escola.negocio.basica.Matricula;
import br.edu.ufape.poo.escola.negocio.basica.Nota;
import br.edu.ufape.poo.escola.negocio.basica.Turma;

@SpringBootTest
@Transactional
class InterfaceRepositorioNotaTest {

    @Autowired
    private InterfaceRepositorioNota repositorio;

    @Test
    void deveSalvarNotaRelacionadaAMatricula() {
        Aluno aluno = new Aluno("Ana", "11122233344", "ana@escola.com", "2026004");
        Disciplina disciplina = new Disciplina("Redes", 40);
        Turma turma = new Turma("B", 2026, disciplina);

        Matricula matricula = new Matricula(aluno, turma);
        Nota nota = new Nota(9.5, matricula);

        Nota salva = repositorio.save(nota);

        assertNotNull(salva.getId());
        assertEquals(9.5, salva.getValor());
        assertEquals("Ana", salva.getMatricula().getAluno().getNome());
    }
}
