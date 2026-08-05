package br.edu.ufape.poo.mensageria.negocio.dados;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.edu.ufape.poo.mensageria.dados.InterfaceRepositorioDisciplina;
import br.edu.ufape.poo.mensageria.negocio.basica.Disciplina;

@SpringBootTest
class InterfaceRepositorioDisciplinaTest {

	@Autowired
	private InterfaceRepositorioDisciplina repositorio;

	@Test
	void deveSalvarEBuscarDisciplina() {
		Disciplina disciplina = new Disciplina("Programacao Orientada a Objetos", 60);

		Disciplina salva = repositorio.save(disciplina);
		Disciplina encontrada = repositorio.findById(salva.getId()).orElseThrow();

		assertNotNull(salva.getId());
		assertEquals("Programacao Orientada a Objetos", encontrada.getNome());
		assertEquals(60, encontrada.getCargaHoraria());
	}
}
