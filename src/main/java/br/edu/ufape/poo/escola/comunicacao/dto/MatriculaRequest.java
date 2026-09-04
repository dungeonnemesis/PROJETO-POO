package br.edu.ufape.poo.escola.comunicacao.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

public record MatriculaRequest(
		@NotNull(message = "A data e obrigatoria") @PastOrPresent(message = "A data nao pode estar no futuro") LocalDate data,
		@NotBlank(message = "O status e obrigatorio") String status,
		@NotNull(message = "O aluno e obrigatorio") @Positive(message = "O aluno e invalido") Long alunoId,
		@NotNull(message = "A turma e obrigatoria") @Positive(message = "A turma e invalida") Long turmaId) {
}
