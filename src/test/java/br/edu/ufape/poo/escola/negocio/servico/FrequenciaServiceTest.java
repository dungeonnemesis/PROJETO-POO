package br.edu.ufape.poo.escola.negocio.servico;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ufape.poo.escola.comunicacao.dto.FrequenciaRequest;
import br.edu.ufape.poo.escola.dados.InterfaceRepositorioAluno;
import br.edu.ufape.poo.escola.dados.InterfaceRepositorioDisciplina;
import br.edu.ufape.poo.escola.dados.InterfaceRepositorioMatricula;
import br.edu.ufape.poo.escola.dados.InterfaceRepositorioProfessor;
import br.edu.ufape.poo.escola.dados.InterfaceRepositorioTurma;
import br.edu.ufape.poo.escola.dados.InterfaceRepositorioTurmaDisciplina;
import br.edu.ufape.poo.escola.negocio.basica.Aluno;
import br.edu.ufape.poo.escola.negocio.basica.Disciplina;
import br.edu.ufape.poo.escola.negocio.basica.Matricula;
import br.edu.ufape.poo.escola.negocio.basica.Professor;
import br.edu.ufape.poo.escola.negocio.basica.StatusFrequencia;
import br.edu.ufape.poo.escola.negocio.basica.Turma;
import br.edu.ufape.poo.escola.negocio.basica.TurmaDisciplina;
import br.edu.ufape.poo.escola.negocio.excecoes.FrequenciaJaRegistradaException;

/**
 * Segue o mesmo estilo de {@code ServicoAlunoTest}: contexto Spring real,
 * transação com rollback ao final de cada teste.
 */
@SpringBootTest
@Transactional
class FrequenciaServiceTest {

	@Autowired private FrequenciaService frequenciaService;
	@Autowired private InterfaceRepositorioAluno alunoRepositorio;
	@Autowired private InterfaceRepositorioDisciplina disciplinaRepositorio;
	@Autowired private InterfaceRepositorioProfessor professorRepositorio;
	@Autowired private InterfaceRepositorioTurma turmaRepositorio;
	@Autowired private InterfaceRepositorioTurmaDisciplina gradeRepositorio;
	@Autowired private InterfaceRepositorioMatricula matriculaRepositorio;

	private Matricula criarMatricula(String sufixo) {
		Aluno aluno = alunoRepositorio
				.save(new Aluno("Aluno " + sufixo, "111222333" + sufixo, sufixo + "@escola.com", "2026" + sufixo));
		Disciplina disciplina = disciplinaRepositorio.save(new Disciplina("Disciplina " + sufixo, 60));
		Professor professor = professorRepositorio
				.save(new Professor("Prof " + sufixo, "555666777" + sufixo, "prof" + sufixo + "@escola.com", "POO"));
		Turma turma = turmaRepositorio.save(new Turma("Turma " + sufixo, 2026));
		TurmaDisciplina grade = gradeRepositorio.save(new TurmaDisciplina(turma, disciplina, professor));
		return matriculaRepositorio.save(new Matricula(LocalDate.now(), "ATIVA", aluno, grade));
	}

	@Test
	void testarCalcularPercentualPresencaComRegistrosMistos() {
		Matricula matricula = criarMatricula("501");
		TurmaDisciplina grade = matricula.getGrade();

		frequenciaService.criar(new FrequenciaRequest(LocalDate.now().minusDays(3), StatusFrequencia.PRESENTE,
				matricula.getId(), grade.getId()));
		frequenciaService.criar(new FrequenciaRequest(LocalDate.now().minusDays(2), StatusFrequencia.AUSENTE,
				matricula.getId(), grade.getId()));
		frequenciaService.criar(new FrequenciaRequest(LocalDate.now().minusDays(1), StatusFrequencia.JUSTIFICADA,
				matricula.getId(), grade.getId()));

		// PRESENTE e JUSTIFICADA contam como frequencia computada: 2 de 3 = 66,67%
		double percentual = frequenciaService.calcularPercentualPresenca(matricula.getId());

		assertEquals(66.67, percentual, 0.5);
	}

	@Test
	void testarCriarFrequenciaDuplicadaDeveFalhar() {
		Matricula matricula = criarMatricula("502");
		TurmaDisciplina grade = matricula.getGrade();
		LocalDate data = LocalDate.now();

		frequenciaService.criar(new FrequenciaRequest(data, StatusFrequencia.PRESENTE, matricula.getId(), grade.getId()));

		assertThrows(FrequenciaJaRegistradaException.class, () -> frequenciaService
				.criar(new FrequenciaRequest(data, StatusFrequencia.AUSENTE, matricula.getId(), grade.getId())));
	}
}
