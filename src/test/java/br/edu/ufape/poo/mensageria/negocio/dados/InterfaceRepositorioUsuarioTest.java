package br.edu.ufape.poo.mensageria.negocio.dados;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.edu.ufape.poo.mensageria.dados.InterfaceRepositorioUsuario;
import br.edu.ufape.poo.mensageria.negocio.basica.Endereco;
import br.edu.ufape.poo.mensageria.negocio.basica.Usuario;

@SpringBootTest
class InterfaceRepositorioUsuarioTest {
	@Autowired
	InterfaceRepositorioUsuario repositorio;

	@Test
	void testeInserirUsuario() {
		Endereco e = new Endereco("Rua a", "s/n", "bairro 1", "Cidade 1", "PE", "CEP");
		Usuario u = new Usuario("Usuario 1", "email", "telefone", e);
		repositorio.save(u);
	}

}
