package br.edu.ufape.poo.escola.comunicacao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record JustificativaRequest(
		@NotNull(message = "A frequencia e obrigatoria") @Positive(message = "A frequencia e invalida") Long frequenciaId,
		@NotBlank(message = "O motivo e obrigatorio") String motivo) {
}
