package br.edu.ufape.poo.escola.negocio.basica;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "turma_disciplina", uniqueConstraints = @UniqueConstraint(columnNames = { "turma_id", "disciplina_id" }))
public class TurmaDisciplina {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false, cascade = jakarta.persistence.CascadeType.PERSIST)
	@JoinColumn(name = "turma_id", nullable = false)
	private Turma turma;

	@ManyToOne(optional = false, cascade = jakarta.persistence.CascadeType.PERSIST)
	@JoinColumn(name = "disciplina_id", nullable = false)
	private Disciplina disciplina;

	@ManyToOne(optional = false, cascade = jakarta.persistence.CascadeType.PERSIST)
	@JoinColumn(name = "professor_id", nullable = false)
	private Professor professor;

	protected TurmaDisciplina() {}

	public TurmaDisciplina(Turma turma, Disciplina disciplina, Professor professor) {
		this.turma = turma;
		this.disciplina = disciplina;
		this.professor = professor;
	}

	public Long getId() { return id; }
	public Turma getTurma() { return turma; }
	public Disciplina getDisciplina() { return disciplina; }
	public Professor getProfessor() { return professor; }
}
