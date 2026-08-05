package br.edu.ufape.poo.mensageria.negocio.dados;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.edu.ufape.poo.mensageria.dados.InterfaceRepositorioTurma;
import br.edu.ufape.poo.mensageria.negocio.basica.Disciplina;
import br.edu.ufape.poo.mensageria.negocio.basica.Turma;

@SpringBootTest
class InterfaceRepositorioTurmaTest {

	@Autowired
	private InterfaceRepositorioTurma repositorio;

	@Test
	void deveSalvarTurmaVinculadaADisciplina() {
		Disciplina disciplina = new Disciplina("Programacao Orientada a Objetos", 60);
		Turma turma = new Turma("Turma 2026.1", 2026, disciplina);

		Turma salva = repositorio.save(turma);
		Turma encontrada = repositorio.findById(salva.getId()).orElseThrow();

		assertNotNull(encontrada.getId());
		assertNotNull(encontrada.getDisciplina().getId());
		assertEquals("Turma 2026.1", encontrada.getNome());
		assertEquals("Programacao Orientada a Objetos", encontrada.getDisciplina().getNome());
	}
}
