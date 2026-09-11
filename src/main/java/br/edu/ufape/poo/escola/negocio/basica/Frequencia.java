package br.edu.ufape.poo.escola.negocio.basica;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import br.edu.ufape.poo.escola.negocio.excecoes.DataFrequenciaInvalidaException;

/**
 * Representa o registro de presença/ausência de um aluno matriculado,
 * em uma data específica, para uma disciplina de uma turma (TurmaDisciplina).
 *
 * Espelha a estrutura de {@link Nota}: associação obrigatória com
 * {@link Matricula} e com {@link TurmaDisciplina}, encapsulamento dos
 * atributos e validação das próprias regras elementares no construtor.
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "matricula_id", "turma_disciplina_id", "data" }))
public class Frequencia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDate data;

	@Enumerated(EnumType.STRING)
	private StatusFrequencia status;

	@ManyToOne(optional = false, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "matricula_id", nullable = false)
	private Matricula matricula;

	@ManyToOne(optional = false, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "turma_disciplina_id", nullable = false)
	private TurmaDisciplina grade;

	protected Frequencia() {}

	public Frequencia(LocalDate data, StatusFrequencia status, Matricula matricula, TurmaDisciplina grade) {
		validarData(data);
		validarStatus(status);
		this.data = data;
		this.status = status;
		this.matricula = matricula;
		this.grade = grade;
	}

	private void validarData(LocalDate data) {
		if (data == null) {
			throw new DataFrequenciaInvalidaException("A data da frequencia e obrigatoria.");
		}
		if (data.isAfter(LocalDate.now())) {
			throw new DataFrequenciaInvalidaException("Nao e possivel registrar frequencia com data futura.");
		}
	}

	private void validarStatus(StatusFrequencia status) {
		if (status == null) {
			throw new DataFrequenciaInvalidaException("O status da frequencia e obrigatorio.");
		}
	}

	public Long getId() { return id; }

	public LocalDate getData() { return data; }
	public void setData(LocalDate data) { validarData(data); this.data = data; }

	public StatusFrequencia getStatus() { return status; }
	public void setStatus(StatusFrequencia status) { validarStatus(status); this.status = status; }

	public Matricula getMatricula() { return matricula; }
	public void setMatricula(Matricula matricula) { this.matricula = matricula; }

	public TurmaDisciplina getGrade() { return grade; }
	public void setGrade(TurmaDisciplina grade) { this.grade = grade; }
}
