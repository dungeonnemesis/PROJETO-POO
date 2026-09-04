package br.edu.ufape.poo.escola.comunicacao.dto;

import br.edu.ufape.poo.escola.negocio.basica.Turma;

public record TurmaResponse(Long id, String nome, Integer ano, DisciplinaResponse disciplina, ProfessorResponse professor) {
	public static TurmaResponse de(Turma turma) {
		return new TurmaResponse(turma.getId(), turma.getNome(), turma.getAno(), DisciplinaResponse.de(turma.getDisciplina()), ProfessorResponse.de(turma.getProfessor()));
	}
}
