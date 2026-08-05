package br.edu.ufape.poo.escola.negocio.basica;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Aluno extends Pessoa {

	@NotBlank
	@Column(nullable = false, unique = true)
	private String matricula;

	protected Aluno() {
		// Construtor exigido pelo JPA.
	}

	public Aluno(String nome, String cpf, String email, String matricula) {
		super(nome, cpf, email);
		this.matricula = matricula;
	}

	@Override
	public String exibirDados() {
		return "Aluno: " + getNome()
				+ " | Matricula: " + matricula
				+ " | E-mail: " + getEmail();
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
}
