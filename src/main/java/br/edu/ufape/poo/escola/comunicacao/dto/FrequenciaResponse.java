package br.edu.ufape.poo.escola.comunicacao.dto;

import java.time.LocalDate;

import br.edu.ufape.poo.escola.negocio.basica.Frequencia;
import br.edu.ufape.poo.escola.negocio.basica.StatusFrequencia;

public record FrequenciaResponse(
		Long id,
		LocalDate data,
		StatusFrequencia status,
		Long matriculaId,
		String aluno,
		String turma,
		String disciplina,
		String professor) {

	public static FrequenciaResponse de(Frequencia frequencia) {
		var matricula = frequencia.getMatricula();
		var grade = frequencia.getGrade();
		return new FrequenciaResponse(
				frequencia.getId(),
				frequencia.getData(),
				frequencia.getStatus(),
				matricula.getId(),
				matricula.getAluno().getNome(),
				matricula.getTurma().getNome(),
				grade.getDisciplina().getNome(),
				grade.getProfessor().getNome());
	}
}
