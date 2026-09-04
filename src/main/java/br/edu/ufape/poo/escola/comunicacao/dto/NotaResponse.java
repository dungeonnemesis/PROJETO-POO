package br.edu.ufape.poo.escola.comunicacao.dto;

import br.edu.ufape.poo.escola.negocio.basica.Nota;

public record NotaResponse(Long id, Double valor, Long matriculaId, String aluno, String turma, String disciplina, String professor) {
	public static NotaResponse de(Nota nota) {
		var matricula = nota.getMatricula();
		var grade = nota.getGrade();
		return new NotaResponse(nota.getId(), nota.getValor(), nota.getMatricula().getId(),
				matricula.getAluno().getNome(), matricula.getTurma().getNome(),
				grade == null ? "Sem disciplina definida" : grade.getDisciplina().getNome(),
				grade == null ? "Sem professor definido" : grade.getProfessor().getNome());
	}
}
