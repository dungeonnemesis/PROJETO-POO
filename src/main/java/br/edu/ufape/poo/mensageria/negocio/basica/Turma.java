package br.edu.ufape.poo.mensageria.negocio.basica;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Turma {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;
	private Integer ano;

	@ManyToOne(optional = false, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "disciplina_id", nullable = false)
	private Disciplina disciplina;

	protected Turma() {
		// Construtor exigido pelo JPA.
	}

	public Turma(String nome, Integer ano, Disciplina disciplina) {
		this.nome = nome;
		this.ano = ano;
		this.disciplina = disciplina;
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}
}
