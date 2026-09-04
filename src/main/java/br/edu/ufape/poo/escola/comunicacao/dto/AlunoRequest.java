package br.edu.ufape.poo.escola.comunicacao.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AlunoRequest(
		@NotBlank(message = "O nome e obrigatorio") String nome,
		@NotBlank(message = "O CPF e obrigatorio") String cpf,
		@NotBlank(message = "O e-mail e obrigatorio") @Email(message = "E-mail invalido") String email) {
}
