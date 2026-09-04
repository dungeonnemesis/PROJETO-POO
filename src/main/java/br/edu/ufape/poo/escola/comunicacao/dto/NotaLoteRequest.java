package br.edu.ufape.poo.escola.comunicacao.dto;

import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record NotaLoteRequest(
        @NotNull(message = "A turma e obrigatoria") @Positive Long turmaId,
        @NotNull(message = "A disciplina e obrigatoria") @Positive Long gradeId,
        @NotEmpty(message = "Informe ao menos uma nota") @Valid List<NotaLoteItem> notas) {
    public record NotaLoteItem(
            @NotNull @Positive Long matriculaId,
            @NotNull Double valor) {}
}
