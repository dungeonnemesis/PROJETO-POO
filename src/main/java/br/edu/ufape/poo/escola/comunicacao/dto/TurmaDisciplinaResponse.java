package br.edu.ufape.poo.escola.comunicacao.dto;

import br.edu.ufape.poo.escola.negocio.basica.TurmaDisciplina;

public record TurmaDisciplinaResponse(Long id, Long turmaId, DisciplinaResponse disciplina, ProfessorResponse professor) {
	public static TurmaDisciplinaResponse de(TurmaDisciplina grade) {
		return new TurmaDisciplinaResponse(grade.getId(), grade.getTurma().getId(),
				DisciplinaResponse.de(grade.getDisciplina()), ProfessorResponse.de(grade.getProfessor()));
	}
}
