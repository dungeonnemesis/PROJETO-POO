package br.edu.ufape.poo.escola.comunicacao.dto;

import java.time.LocalDate;
import java.util.List;

import br.edu.ufape.poo.escola.negocio.basica.StatusFrequencia;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record FrequenciaLoteRequest(
		@NotNull(message = "A turma e obrigatoria") @Positive Long turmaId,
		@NotNull(message = "A disciplina e obrigatoria") @Positive Long gradeId,
		@NotNull(message = "A data e obrigatoria") LocalDate data,
		@NotEmpty(message = "Informe ao menos um registro") @Valid List<FrequenciaLoteItem> registros) {

	public record FrequenciaLoteItem(
			@NotNull @Positive Long matriculaId,
			@NotNull StatusFrequencia status) {
	}
}
