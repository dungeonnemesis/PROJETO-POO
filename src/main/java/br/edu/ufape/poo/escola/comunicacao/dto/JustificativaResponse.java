package br.edu.ufape.poo.escola.comunicacao.dto;

import java.time.LocalDate;

import br.edu.ufape.poo.escola.negocio.basica.Justificativa;

public record JustificativaResponse(
		Long id,
		String motivo,
		LocalDate dataRegistro,
		Long frequenciaId,
		String aluno,
		LocalDate dataFalta) {

	public static JustificativaResponse de(Justificativa justificativa) {
		var frequencia = justificativa.getFrequencia();
		return new JustificativaResponse(
				justificativa.getId(),
				justificativa.getMotivo(),
				justificativa.getDataRegistro(),
				frequencia.getId(),
				frequencia.getMatricula().getAluno().getNome(),
				frequencia.getData());
	}
}
