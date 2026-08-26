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
import br.edu.ufape.poo.escola.negocio.basica.Turma;

@SpringBootTest
@Transactional
class InterfaceRepositorioMatriculaTest {

    @Autowired
    private InterfaceRepositorioMatricula repositorio;

    @Test
    void deveSalvarMatriculaComAlunoETurma() {
        Aluno aluno = new Aluno("João", "98765432100", "joao@escola.com", "2026003");
        Disciplina disciplina = new Disciplina("Algoritmos", 80);
        Turma turma = new Turma("A", 2026, disciplina);

        Matricula matricula = new Matricula(aluno, turma);

        Matricula salva = repositorio.save(matricula);

        assertNotNull(salva.getId());
        assertEquals("João", salva.getAluno().getNome());
        assertEquals("A", salva.getTurma().getNome());
    }
}
