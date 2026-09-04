package br.edu.ufape.poo.escola.comunicacao;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import br.edu.ufape.poo.escola.negocio.basica.Aluno;
import br.edu.ufape.poo.escola.negocio.basica.Disciplina;
import br.edu.ufape.poo.escola.negocio.basica.Professor;
import br.edu.ufape.poo.escola.negocio.basica.Turma;
import br.edu.ufape.poo.escola.negocio.excecao.RecursoNaoEncontradoException;
import br.edu.ufape.poo.escola.negocio.servico.AlunoService;
import br.edu.ufape.poo.escola.negocio.servico.DisciplinaService;
import br.edu.ufape.poo.escola.negocio.servico.ProfessorService;
import br.edu.ufape.poo.escola.negocio.servico.TurmaService;

class ApiControllerTest {
	private MockMvc mvc;
	private LocalValidatorFactoryBean validator;
	private AlunoService alunoService;
	private ProfessorService professorService;
	private DisciplinaService disciplinaService;
	private TurmaService turmaService;

	@BeforeEach
	void configurar() {
		alunoService = mock(AlunoService.class);
		professorService = mock(ProfessorService.class);
		disciplinaService = mock(DisciplinaService.class);
		turmaService = mock(TurmaService.class);
		validator = new LocalValidatorFactoryBean();
		validator.afterPropertiesSet();
		mvc = MockMvcBuilders.standaloneSetup(
				new AlunoController(alunoService), new ProfessorController(professorService),
				new DisciplinaController(disciplinaService), new TurmaController(turmaService))
				.setControllerAdvice(new ApiExceptionHandler()).setValidator(validator).build();
	}

	@AfterEach void fechar() { validator.close(); }

	@Test
	void deveListarAlunos() throws Exception {
		when(alunoService.listar()).thenReturn(List.of(new Aluno("Ana", "11122233344", "ana@escola.com", "2026001")));
		mvc.perform(get("/api/alunos")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].nome").value("Ana"))
				.andExpect(jsonPath("$[0].matricula").value("2026001"));
	}

	@Test
	void deveRejeitarAlunoInvalido() throws Exception {
		mvc.perform(post("/api/alunos").contentType(MediaType.APPLICATION_JSON)
				.content("{\"nome\":\"\",\"cpf\":\"\",\"email\":\"invalido\",\"matricula\":\"\"}"))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.mensagem").value("Dados invalidos"))
				.andExpect(jsonPath("$.campos.email").value("E-mail invalido"));
	}

	@Test
	void deveCriarProfessor() throws Exception {
		Professor professor = new Professor("Carlos", "55566677788", "carlos@escola.com", "POO");
		when(professorService.criar(any())).thenReturn(professor);
		mvc.perform(post("/api/professores").contentType(MediaType.APPLICATION_JSON)
				.content("{\"nome\":\"Carlos\",\"cpf\":\"55566677788\",\"email\":\"carlos@escola.com\",\"especialidade\":\"POO\"}"))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.especialidade").value("POO"));
	}

	@Test
	void deveValidarDisciplina() throws Exception {
		mvc.perform(post("/api/disciplinas").contentType(MediaType.APPLICATION_JSON)
				.content("{\"nome\":\"Banco de Dados\",\"cargaHoraria\":0}"))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.campos.cargaHoraria").exists());
	}

	@Test
	void deveBuscarTurmaComDisciplina() throws Exception {
		Disciplina disciplina = new Disciplina("POO", 60);
		Professor professor = new Professor("Carlos", "55566677788", "carlos@escola.com", "POO");
		when(turmaService.buscar(1L)).thenReturn(new Turma("Turma A", 2026, disciplina, professor));
		mvc.perform(get("/api/turmas/1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.nome").value("Turma A"))
				.andExpect(jsonPath("$.disciplina.nome").value("POO"))
				.andExpect(jsonPath("$.professor.nome").value("Carlos"));
	}

	@Test
	void deveRejeitarTurmaSemDisciplina() throws Exception {
		mvc.perform(post("/api/turmas").contentType(MediaType.APPLICATION_JSON)
				.content("{\"nome\":\"Turma A\",\"ano\":2026}"))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.campos.disciplinaId").exists());
	}

	@Test
	void deveResponder404QuandoRecursoNaoExiste() throws Exception {
		when(disciplinaService.buscar(99L)).thenThrow(new RecursoNaoEncontradoException("Disciplina", 99L));
		mvc.perform(get("/api/disciplinas/99")).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404)).andExpect(jsonPath("$.mensagem").value("Disciplina de id 99 nao encontrado(a)"));
	}

	@Test
	void deveExcluirAluno() throws Exception {
		doNothing().when(alunoService).excluir(1L);
		mvc.perform(delete("/api/alunos/1")).andExpect(status().isNoContent()).andExpect(content().string(""));
	}
}
