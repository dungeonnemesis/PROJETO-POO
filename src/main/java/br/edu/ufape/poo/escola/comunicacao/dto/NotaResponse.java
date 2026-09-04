package br.edu.ufape.poo.escola.comunicacao.dto;

import br.edu.ufape.poo.escola.negocio.basica.Nota;

public record NotaResponse(Long id, Double valor, Long matriculaId, String aluno, String turma) {
	public static NotaResponse de(Nota nota) {
		return new NotaResponse(nota.getId(), nota.getValor(), nota.getMatricula().getId(),
				nota.getMatricula().getAluno().getNome(), nota.getMatricula().getTurma().getNome());
	}
}
