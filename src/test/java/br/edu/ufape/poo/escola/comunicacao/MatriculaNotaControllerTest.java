package br.edu.ufape.poo.escola.comunicacao;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import br.edu.ufape.poo.escola.negocio.basica.*;
import br.edu.ufape.poo.escola.negocio.servico.MatriculaService;
import br.edu.ufape.poo.escola.negocio.servico.NotaService;

class MatriculaNotaControllerTest {
	private MockMvc mvc;
	private LocalValidatorFactoryBean validator;
	private MatriculaService matriculaService;
	private NotaService notaService;

	@BeforeEach void configurar() {
		matriculaService = mock(MatriculaService.class); notaService = mock(NotaService.class);
		validator = new LocalValidatorFactoryBean(); validator.afterPropertiesSet();
		mvc = MockMvcBuilders.standaloneSetup(new MatriculaController(matriculaService), new NotaController(notaService))
				.setControllerAdvice(new ApiExceptionHandler()).setValidator(validator).build();
	}
	@AfterEach void fechar() { validator.close(); }

	@Test void deveRejeitarNotaAcimaDeDez() throws Exception {
		mvc.perform(post("/api/notas").contentType(MediaType.APPLICATION_JSON).content("{\"valor\":11,\"matriculaId\":1}"))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.campos.valor").value("A nota maxima e dez"));
	}

	@Test void deveCriarMatricula() throws Exception {
		Aluno aluno = new Aluno("Ana", "11122233344", "ana@escola.com", "2026001");
		Professor professor = new Professor("Carlos", "55566677788", "carlos@escola.com", "POO");
		Turma turma = new Turma("Turma A", 2026, new Disciplina("POO", 60), professor);
		when(matriculaService.criar(any())).thenReturn(new Matricula(LocalDate.now(), "ATIVA", aluno, turma));
		mvc.perform(post("/api/matriculas").contentType(MediaType.APPLICATION_JSON)
				.content("{\"data\":\"" + LocalDate.now() + "\",\"status\":\"ATIVA\",\"alunoId\":1,\"turmaId\":1}"))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.status").value("ATIVA"))
				.andExpect(jsonPath("$.aluno.nome").value("Ana"));
	}
}
