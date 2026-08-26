package br.edu.ufape.poo.escola.dados;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ufape.poo.escola.negocio.basica.Aluno;

@SpringBootTest
@Transactional
class InterfaceRepositorioAlunoTest {

	@Autowired
	private InterfaceRepositorioAluno repositorio;

	@Test
	void deveSalvarEBuscarAlunoPorMatricula() {
		Aluno aluno = new Aluno(
				"Ana Souza",
				"11122233344",
				"ana@escola.com",
				"2026001");

		Aluno salvo = repositorio.save(aluno);
		Aluno encontrado = repositorio.findByMatricula("2026001");

		assertNotNull(salvo.getId());
		assertNotNull(encontrado);
		assertEquals("Ana Souza", encontrado.getNome());
		assertEquals("2026001", encontrado.getMatricula());
		assertEquals(
				"Aluno: Ana Souza | Matricula: 2026001 | E-mail: ana@escola.com",
				encontrado.exibirDados());
	}
}
