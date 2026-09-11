package br.edu.ufape.poo.escola.comunicacao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Teste de interface: sobe o contexto Spring completo (servindo os arquivos
 * estáticos de fato) e confirma que a tela principal é servida com sucesso
 * e que o app.js entregue já inclui a aba/formulário de Frequência.
 *
 * Optamos por não executar o JavaScript de verdade (via HtmlUnit) porque o
 * motor de JS do HtmlUnit (um fork do Rhino) tem problemas conhecidos para
 * interpretar "async function" — usado em todo o app.js, inclusive em
 * código que já existia antes desta VA. Reescrever o app.js inteiro para
 * contornar essa limitação do motor de teste, sem conseguir validar
 * localmente antes da entrega, é um risco desproporcional ao benefício.
 *
 * O corpo da resposta é lido explicitamente como UTF-8: por padrão,
 * MockHttpServletResponse#getContentAsString() assume ISO-8859-1 quando o
 * servidor não declara charset no Content-Type do .js, o que corrompe
 * acentos (ex.: "Frequências" vira "FrequÃªncias").
 */
@SpringBootTest
class FrequenciaInterfaceTest {

	@Autowired
	private WebApplicationContext contexto;

	private MockMvc mockMvc;

	@BeforeEach
	void configurar() {
		mockMvc = MockMvcBuilders.webAppContextSetup(contexto).build();
	}

	@Test
	void telaPrincipalDeveSerServidaComSucesso() throws Exception {
		mockMvc.perform(get("/index.html")).andExpect(status().isOk());
	}

	@Test
	void telaDeveIncluirAAbaEOFormularioDeFrequencia() throws Exception {
		String conteudoScript = mockMvc.perform(get("/app.js"))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

		// Confirma que a tela conhece o recurso 'frequencias' (aba/label) e
		// que o formulário dedicado (freqData/freqStatus) está de fato
		// integrado ao app.js servido pelo backend, não só no arquivo local.
		assertThat(conteudoScript).contains("frequencias:");
		assertThat(conteudoScript).contains("Frequências");
		assertThat(conteudoScript).contains("buildFrequenciaForm");
		assertThat(conteudoScript).contains("freqStatus");
	}
}
