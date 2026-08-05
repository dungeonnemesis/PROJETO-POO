package br.edu.ufape.poo.escola.negocio.basica;

import jakarta.persistence.Entity;

@Entity
public class Aluno extends Pessoa {

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
		return "Aluno: " + getNome() + ", matricula: " + matricula;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
}
