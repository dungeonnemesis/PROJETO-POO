package br.edu.ufape.poo.escola.dados;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ufape.poo.escola.negocio.basica.Professor;

@SpringBootTest
@Transactional
class InterfaceRepositorioProfessorTest {

	@Autowired
	private InterfaceRepositorioProfessor repositorio;

	@Test
	void deveSalvarEBuscarProfessor() {
		Professor professor = new Professor(
				"Carlos Lima",
				"55566677788",
				"carlos@escola.com",
				"Programacao Orientada a Objetos");

		Professor salvo = repositorio.save(professor);
		Professor encontrado = repositorio.findById(salvo.getId()).orElseThrow();

		assertNotNull(encontrado.getId());
		assertEquals("Carlos Lima", encontrado.getNome());
		assertEquals("Programacao Orientada a Objetos", encontrado.getEspecialidade());
	}
}
