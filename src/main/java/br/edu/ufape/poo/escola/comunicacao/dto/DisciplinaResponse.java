package br.edu.ufape.poo.escola.comunicacao.dto;

import br.edu.ufape.poo.escola.negocio.basica.Disciplina;

public record DisciplinaResponse(Long id, String nome, Integer cargaHoraria) {
	public static DisciplinaResponse de(Disciplina disciplina) {
		return new DisciplinaResponse(disciplina.getId(), disciplina.getNome(), disciplina.getCargaHoraria());
	}
}
