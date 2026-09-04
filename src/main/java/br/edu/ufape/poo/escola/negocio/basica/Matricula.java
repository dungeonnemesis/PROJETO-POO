package br.edu.ufape.poo.escola.negocio.basica;

import java.time.LocalDate;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"aluno_id", "turma_disciplina_id"}))
public class Matricula {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate data;
	private String status;

	@ManyToOne(optional = false, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "aluno_id", nullable = false)
	private Aluno aluno;

	@ManyToOne(optional = false, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "turma_id", nullable = false)
	private Turma turma;

	@ManyToOne
	@JoinColumn(name = "turma_disciplina_id")
	private TurmaDisciplina grade;

	protected Matricula() {}

	public Matricula(LocalDate data, String status, Aluno aluno, Turma turma) {
		this.data = data;
		this.status = status;
		this.aluno = aluno;
		this.turma = turma;
	}

	public Matricula(LocalDate data, String status, Aluno aluno, TurmaDisciplina grade) {
		this(data, status, aluno, grade.getTurma());
		this.grade = grade;
	}

	public Matricula(Aluno aluno, Turma turma) {
		this(LocalDate.now(), "ATIVA", aluno, turma);
	}

	public Long getId() { return id; }
	public LocalDate getData() { return data; }
	public void setData(LocalDate data) { this.data = data; }
	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }
	public Aluno getAluno() { return aluno; }
	public void setAluno(Aluno aluno) { this.aluno = aluno; }
	public Turma getTurma() { return turma; }
	public void setTurma(Turma turma) { this.turma = turma; }
	public TurmaDisciplina getGrade() { return grade; }
	public void setGrade(TurmaDisciplina grade) { this.grade = grade; if (grade != null) this.turma = grade.getTurma(); }
}
