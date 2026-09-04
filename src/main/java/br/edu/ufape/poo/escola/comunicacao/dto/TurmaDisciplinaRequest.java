package br.edu.ufape.poo.escola.comunicacao.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TurmaDisciplinaRequest(
		@NotNull(message = "A disciplina e obrigatoria") @Positive(message = "A disciplina e invalida") Long disciplinaId,
		@NotNull(message = "O professor e obrigatorio") @Positive(message = "O professor e invalido") Long professorId) {}
