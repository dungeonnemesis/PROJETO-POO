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

import br.edu.ufape.poo.escola.negocio.basica.Aluno;
import br.edu.ufape.poo.escola.negocio.basica.Disciplina;
import br.edu.ufape.poo.escola.negocio.basica.Frequencia;
import br.edu.ufape.poo.escola.negocio.basica.Matricula;
import br.edu.ufape.poo.escola.negocio.basica.Professor;
import br.edu.ufape.poo.escola.negocio.basica.StatusFrequencia;
import br.edu.ufape.poo.escola.negocio.basica.Turma;
import br.edu.ufape.poo.escola.negocio.basica.TurmaDisciplina;
import br.edu.ufape.poo.escola.negocio.excecoes.FrequenciaJaRegistradaException;
import br.edu.ufape.poo.escola.negocio.servico.FrequenciaService;

class FrequenciaControllerTest {

	private MockMvc mvc;
	private LocalValidatorFactoryBean validator;
	private FrequenciaService servico;

	@BeforeEach
	void configurar() {
		servico = mock(FrequenciaService.class);
		validator = new LocalValidatorFactoryBean();
		validator.afterPropertiesSet();
		mvc = MockMvcBuilders.standaloneSetup(new FrequenciaController(servico))
				.setControllerAdvice(new ApiExceptionHandler()).setValidator(validator).build();
	}

	@AfterEach
	void fechar() {
		validator.close();
	}

	@Test
	void deveCriarFrequenciaComSucesso() throws Exception {
		Aluno aluno = new Aluno("Ana", "11122233344", "ana@escola.com", "2026005");
		Disciplina disciplina = new Disciplina("POO", 60);
		Professor professor = new Professor("Carlos", "55566677788", "carlos@escola.com", "POO");
		Turma turma = new Turma("Turma A", 2026, disciplina, professor);
		TurmaDisciplina grade = turma.getGrade().get(0);
		Matricula matricula = new Matricula(aluno, turma);
		Frequencia frequencia = new Frequencia(LocalDate.now(), StatusFrequencia.PRESENTE, matricula, grade);

		when(servico.criar(any())).thenReturn(frequencia);

		mvc.perform(post("/api/frequencias").contentType(MediaType.APPLICATION_JSON)
				.content("{\"data\":\"" + LocalDate.now() + "\",\"status\":\"PRESENTE\",\"matriculaId\":1,\"gradeId\":1}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.status").value("PRESENTE"))
				.andExpect(jsonPath("$.aluno").value("Ana"));
	}

	@Test
	void deveResponder409QuandoFrequenciaDuplicada() throws Exception {
		when(servico.criar(any())).thenThrow(new FrequenciaJaRegistradaException(
				"Ja existe frequencia registrada para esta matricula, disciplina e data."));

		mvc.perform(post("/api/frequencias").contentType(MediaType.APPLICATION_JSON)
				.content("{\"data\":\"" + LocalDate.now() + "\",\"status\":\"PRESENTE\",\"matriculaId\":1,\"gradeId\":1}"))
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.mensagem")
						.value("Ja existe frequencia registrada para esta matricula, disciplina e data."));
	}

	@Test
	void deveRejeitarFrequenciaSemStatus() throws Exception {
		mvc.perform(post("/api/frequencias").contentType(MediaType.APPLICATION_JSON)
				.content("{\"data\":\"" + LocalDate.now() + "\",\"matriculaId\":1,\"gradeId\":1}"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.campos.status").value("O status e obrigatorio"));
	}
}
