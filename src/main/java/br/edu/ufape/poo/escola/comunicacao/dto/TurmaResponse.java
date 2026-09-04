package br.edu.ufape.poo.escola.comunicacao.dto;

import br.edu.ufape.poo.escola.negocio.basica.Turma;

import java.util.List;
import java.util.stream.Collectors;

public record TurmaResponse(Long id, String nome, Integer ano, String turno, List<TurmaDisciplinaResponse> grade) {
	public static TurmaResponse de(Turma turma) {
		return new TurmaResponse(turma.getId(), turma.getNome(), turma.getAno(), turma.getTurno(), turma.getGrade().stream().map(TurmaDisciplinaResponse::de).collect(Collectors.toList()));
	}
}
