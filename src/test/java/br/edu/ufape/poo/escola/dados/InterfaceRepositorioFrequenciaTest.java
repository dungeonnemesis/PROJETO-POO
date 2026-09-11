package br.edu.ufape.poo.escola.dados;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ufape.poo.escola.negocio.basica.Aluno;
import br.edu.ufape.poo.escola.negocio.basica.Disciplina;
import br.edu.ufape.poo.escola.negocio.basica.Frequencia;
import br.edu.ufape.poo.escola.negocio.basica.Matricula;
import br.edu.ufape.poo.escola.negocio.basica.Professor;
import br.edu.ufape.poo.escola.negocio.basica.StatusFrequencia;
import br.edu.ufape.poo.escola.negocio.basica.Turma;
import br.edu.ufape.poo.escola.negocio.basica.TurmaDisciplina;

@SpringBootTest
@Transactional
class InterfaceRepositorioFrequenciaTest {

	@Autowired private InterfaceRepositorioFrequencia repositorio;
	@Autowired private InterfaceRepositorioAluno alunoRepositorio;
	@Autowired private InterfaceRepositorioDisciplina disciplinaRepositorio;
	@Autowired private InterfaceRepositorioProfessor professorRepositorio;
	@Autowired private InterfaceRepositorioTurma turmaRepositorio;
	@Autowired private InterfaceRepositorioTurmaDisciplina gradeRepositorio;
	@Autowired private InterfaceRepositorioMatricula matriculaRepositorio;

	@Test
	void deveSalvarFrequenciaRelacionadaAMatriculaEGrade() {
		Aluno aluno = alunoRepositorio.save(new Aluno("Bruno", "22233344455", "bruno@escola.com", "2026601"));
		Disciplina disciplina = disciplinaRepositorio.save(new Disciplina("Estruturas de Dados", 60));
		Professor professor = professorRepositorio.save(new Professor("Marta", "66677788899", "marta@escola.com", "POO"));
		Turma turma = turmaRepositorio.save(new Turma("Turma C", 2026));
		TurmaDisciplina grade = gradeRepositorio.save(new TurmaDisciplina(turma, disciplina, professor));
		Matricula matricula = matriculaRepositorio.save(new Matricula(LocalDate.now(), "ATIVA", aluno, grade));

		Frequencia salva = repositorio.save(new Frequencia(LocalDate.now(), StatusFrequencia.PRESENTE, matricula, grade));

		assertNotNull(salva.getId());
		assertEquals(StatusFrequencia.PRESENTE, salva.getStatus());
		assertEquals("Bruno", salva.getMatricula().getAluno().getNome());
	}

	@Test
	void deveDetectarFrequenciaJaRegistradaParaMesmaMatriculaGradeEData() {
		Aluno aluno = alunoRepositorio.save(new Aluno("Carla", "33344455566", "carla@escola.com", "2026602"));
		Disciplina disciplina = disciplinaRepositorio.save(new Disciplina("Redes", 40));
		Professor professor = professorRepositorio.save(new Professor("Diego", "77788899900", "diego@escola.com", "Redes"));
		Turma turma = turmaRepositorio.save(new Turma("Turma D", 2026));
		TurmaDisciplina grade = gradeRepositorio.save(new TurmaDisciplina(turma, disciplina, professor));
		Matricula matricula = matriculaRepositorio.save(new Matricula(LocalDate.now(), "ATIVA", aluno, grade));
		LocalDate data = LocalDate.now();

		repositorio.save(new Frequencia(data, StatusFrequencia.PRESENTE, matricula, grade));

		assertTrue(repositorio.existsByMatriculaIdAndGradeIdAndData(matricula.getId(), grade.getId(), data));
		assertFalse(repositorio.existsByMatriculaIdAndGradeIdAndData(matricula.getId(), grade.getId(), data.minusDays(1)));
	}
}
