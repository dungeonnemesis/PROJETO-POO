package br.edu.ufape.poo.escola.comunicacao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DisciplinaRequest(
		@NotBlank(message = "O nome e obrigatorio") String nome,
		@NotNull(message = "A carga horaria e obrigatoria") @Positive(message = "A carga horaria deve ser positiva") Integer cargaHoraria) {
}
