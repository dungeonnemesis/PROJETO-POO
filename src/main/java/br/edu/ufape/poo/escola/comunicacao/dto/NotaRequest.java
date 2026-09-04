package br.edu.ufape.poo.escola.comunicacao.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record NotaRequest(
		@NotNull(message = "O valor e obrigatorio")
		@DecimalMin(value = "0.0", message = "A nota minima e zero")
		@DecimalMax(value = "10.0", message = "A nota maxima e dez") Double valor,
		@NotNull(message = "A matricula e obrigatoria") @Positive(message = "A matricula e invalida") Long matriculaId) {
}
