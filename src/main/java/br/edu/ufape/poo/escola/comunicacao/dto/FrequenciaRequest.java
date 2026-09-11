package br.edu.ufape.poo.escola.comunicacao.dto;

import java.time.LocalDate;

import br.edu.ufape.poo.escola.negocio.basica.StatusFrequencia;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record FrequenciaRequest(
		@NotNull(message = "A data e obrigatoria") LocalDate data,
		@NotNull(message = "O status e obrigatorio") StatusFrequencia status,
		@NotNull(message = "A matricula e obrigatoria") @Positive(message = "A matricula e invalida") Long matriculaId,
		@NotNull(message = "A disciplina e obrigatoria") @Positive(message = "A disciplina e invalida") Long gradeId) {
}
