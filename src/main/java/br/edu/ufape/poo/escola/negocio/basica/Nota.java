package br.edu.ufape.poo.escola.negocio.basica;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Nota {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Double valor;

	@ManyToOne(optional = false, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "matricula_id", nullable = false)
	private Matricula matricula;

	@ManyToOne
	@JoinColumn(name = "turma_disciplina_id")
	private TurmaDisciplina grade;

	protected Nota() {}

	public Nota(Double valor, Matricula matricula) {
		this.valor = valor;
		this.matricula = matricula;
	}

	public Nota(Double valor, Matricula matricula, TurmaDisciplina grade) {
		this(valor, matricula);
		this.grade = grade;
	}

	public Long getId() { return id; }
	public Double getValor() { return valor; }
	public void setValor(Double valor) { this.valor = valor; }
	public Matricula getMatricula() { return matricula; }
	public void setMatricula(Matricula matricula) { this.matricula = matricula; }
	public TurmaDisciplina getGrade() { return grade; }
	public void setGrade(TurmaDisciplina grade) { this.grade = grade; }
}
