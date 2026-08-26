package br.edu.ufape.poo.escola.dados;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ufape.poo.escola.negocio.basica.Aluno;
import br.edu.ufape.poo.escola.negocio.basica.Pessoa;

@SpringBootTest
@Transactional
class InterfaceRepositorioPessoaTest {

	@Autowired
	private InterfaceRepositorioPessoa repositorio;

	@Test
	void deveSalvarEBuscarPessoaPorCpfEEmail() {
		Pessoa pessoa = new Aluno(
				"Maria Silva",
				"12345678900",
				"maria@escola.com",
				"2026002");

		Pessoa salva = repositorio.save(pessoa);
		Pessoa encontradaPorCpf = repositorio.findByCpf("12345678900");
		Pessoa encontradaPorEmail = repositorio.findByEmail("maria@escola.com");

		assertNotNull(salva.getId());
		assertNotNull(encontradaPorCpf);
		assertNotNull(encontradaPorEmail);
		assertEquals("Maria Silva", encontradaPorCpf.getNome());
		assertEquals(salva.getId(), encontradaPorEmail.getId());
	}
}
