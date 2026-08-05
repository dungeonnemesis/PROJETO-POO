package br.edu.ufape.poo.escola.dados;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ufape.poo.escola.negocio.basica.Disciplina;

@SpringBootTest
@Transactional
class InterfaceRepositorioDisciplinaTest {

	@Autowired
	private InterfaceRepositorioDisciplina repositorio;

	@Test
	void deveSalvarEBuscarDisciplinaPorNome() {
		Disciplina disciplina = new Disciplina("Banco de Dados", 60);

		Disciplina salva = repositorio.save(disciplina);
		Disciplina encontrada = repositorio.findByNome("Banco de Dados");

		assertNotNull(salva.getId());
		assertNotNull(encontrada);
		assertEquals(60, encontrada.getCargaHoraria());
	}
}
