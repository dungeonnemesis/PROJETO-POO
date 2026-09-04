package br.edu.ufape.poo.escola.negocio.basica;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Turma {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;
	private Integer ano;
	private String turno;

	@OneToMany(mappedBy = "turma", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<TurmaDisciplina> grade = new ArrayList<>();

	protected Turma() {
		// Construtor exigido pelo JPA.
	}

	public Turma(String nome, Integer ano) {
		this.nome = nome;
		this.ano = ano;
	}

	public Turma(String nome, Integer ano, String turno) {
		this.nome = nome;
		this.ano = ano;
		this.turno = turno;
	}

	public Turma(String nome, Integer ano, Disciplina disciplina, Professor professor) {
		this.nome = nome;
		this.ano = ano;
		if (disciplina != null && professor != null) grade.add(new TurmaDisciplina(this, disciplina, professor));
	}

	public Turma(String nome, Integer ano, Disciplina disciplina) {
		this.nome = nome;
		this.ano = ano;
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

	public String getTurno() { return turno; }
	public void setTurno(String turno) { this.turno = turno; }

	public List<TurmaDisciplina> getGrade() { return grade; }

	public Disciplina getDisciplina() { return grade.isEmpty() ? null : grade.get(0).getDisciplina(); }
	public Professor getProfessor() { return grade.isEmpty() ? null : grade.get(0).getProfessor(); }
	public void setDisciplina(Disciplina disciplina) { }
	public void setProfessor(Professor professor) { }
}
