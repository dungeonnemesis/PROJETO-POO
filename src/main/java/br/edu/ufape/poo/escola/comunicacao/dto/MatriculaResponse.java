package br.edu.ufape.poo.escola.comunicacao.dto;

import java.time.LocalDate;
import br.edu.ufape.poo.escola.negocio.basica.Matricula;

public record MatriculaResponse(Long id, LocalDate data, String status, AlunoResponse aluno, TurmaResponse turma) {
	public static MatriculaResponse de(Matricula matricula) {
		return new MatriculaResponse(matricula.getId(), matricula.getData(), matricula.getStatus(),
				AlunoResponse.de(matricula.getAluno()), TurmaResponse.de(matricula.getTurma()));
	}
}
