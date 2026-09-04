package br.edu.ufape.poo.escola.comunicacao.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TurmaRequest(
		@NotBlank(message = "O nome e obrigatorio") String nome,
		@NotNull(message = "O ano e obrigatorio") @Min(value = 2000, message = "Ano invalido") @Max(value = 2100, message = "Ano invalido") Integer ano,
		@NotBlank(message = "O turno e obrigatorio") String turno) {
}
