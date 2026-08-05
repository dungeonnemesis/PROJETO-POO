package br.edu.ufape.poo.escola.negocio.basica;

import jakarta.persistence.Entity;

@Entity
public class Professor extends Pessoa {

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
		return "Professor: " + getNome() + ", especialidade: " + especialidade;
	}

	public String getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
	}
}
