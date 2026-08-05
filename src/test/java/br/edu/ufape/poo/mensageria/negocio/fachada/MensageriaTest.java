package br.edu.ufape.poo.mensageria.negocio.fachada;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.edu.ufape.poo.mensageria.negocio.basica.Endereco;
import br.edu.ufape.poo.mensageria.negocio.basica.Grupo;
import br.edu.ufape.poo.mensageria.negocio.basica.Usuario;
import br.edu.ufape.poo.mensageria.negocio.cadastro.RegistroDuplicadoException;
import jakarta.transaction.Transactional;

@SpringBootTest
class MensageriaTest {
	@Autowired
	private Mensageria mensageria;

	@Test
	@Transactional
	void testeIngressoGrupo() {
		Grupo g1 = new Grupo("Amigos da rua", null);
		Endereco e = new Endereco("Rua a", "s/n", "Centro", "Garanhuns", "PE", "55.555-00");
		Usuario u = new Usuario("Fulano", "fulano@gmail.com", "87 - 9999.9999", e);
		
		try {
			mensageria.salvarGrupo(g1);
			mensageria.salvarUsuario(u);
			int qtdGrupoAntes = u.getGruposInscritos().size();
			mensageria.ingressarEmUmGrupo(u.getId(), g1.getId());
			Usuario u2 = mensageria.carregarUsuario(u.getId());
			int qtdGrupoDepois = u2.getGruposInscritos().size();
			
			assertEquals(qtdGrupoAntes + 1, qtdGrupoDepois);
			
			
		} catch (Exception e1) {
			fail(e1.getMessage());
		}
	}		

}
