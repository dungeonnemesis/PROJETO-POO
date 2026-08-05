package br.edu.ufape.poo.escola.dados;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ufape.poo.escola.negocio.basica.Pessoa;
import jakarta.persistence.Entity;

@SpringBootTest
@Transactional
class InterfaceRepositorioPessoaTest {

	@Autowired
	private InterfaceRepositorioPessoa repositorio;

	@Test
	void deveSalvarEBuscarPessoaPorCpfEEmail() {
		Pessoa pessoa = new PessoaTeste(
				"Maria Silva",
				"12345678900",
				"maria@escola.com");

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

@Entity
class PessoaTeste extends Pessoa {

	protected PessoaTeste() {
		// Construtor exigido pelo JPA.
	}

	PessoaTeste(String nome, String cpf, String email) {
		super(nome, cpf, email);
	}

	@Override
	public String exibirDados() {
		return getNome() + " - " + getEmail();
	}
}
