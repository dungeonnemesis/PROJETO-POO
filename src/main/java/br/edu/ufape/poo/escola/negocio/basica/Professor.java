package br.edu.ufape.poo.escola.negocio.basica;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Professor extends Pessoa {

	@NotBlank
	@Column(nullable = false)
	private String especialidade;

	protected Professor() {
		// Construtor exigido pelo JPA.
	}

	public Professor(String nome, String cpf, String email, String especialidade) {
		super(nome, cpf, email);
		this.especialidade = especialidade;
	}

	@Override
	public String exibirDados() {
		return "Professor: " + getNome()
				+ " | Especialidade: " + especialidade
				+ " | E-mail: " + getEmail();
	}

	public String getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
	}
}
