package br.edu.ufape.poo.mensageria.negocio.cadastro;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.edu.ufape.poo.mensageria.negocio.basica.Grupo;

@SpringBootTest
class CadastroGrupoTest {
	@Autowired
	private InterfaceCadastroGrupo cadastroGrupo;

	@Test
	public void testSalvarGrupoValido() {
		//Inicialização
		Grupo g = new Grupo("Grupo Teste", null);
		
		//Interação
		try {
			cadastroGrupo.salvarGrupo(g);
			assertTrue(g.getId() != 0);
		} catch (RegistroDuplicadoException e) {
			fail();
		}
	}

}
