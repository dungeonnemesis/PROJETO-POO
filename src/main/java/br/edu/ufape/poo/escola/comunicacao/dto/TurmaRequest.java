package br.edu.ufape.poo.escola.comunicacao.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TurmaRequest(
		@NotBlank(message = "O nome e obrigatorio") String nome,
		@NotNull(message = "O ano e obrigatorio") @Min(value = 2000, message = "Ano invalido") @Max(value = 2100, message = "Ano invalido") Integer ano,
		@NotNull(message = "A disciplina e obrigatoria") @Positive(message = "A disciplina e invalida") Long disciplinaId,
		@NotNull(message = "O professor e obrigatorio") @Positive(message = "O professor e invalido") Long professorId) {
}
