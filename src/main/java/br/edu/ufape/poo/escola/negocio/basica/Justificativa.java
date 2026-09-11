package br.edu.ufape.poo.escola.negocio.basica;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

/**
 * Justificativa de uma falta (Frequencia com status AUSENTE).
 * Associação 1:1 com {@link Frequencia} — cada falta pode ter, no máximo,
 * uma justificativa vinculada.
 */
@Entity
public class Justificativa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String motivo;
	private LocalDate dataRegistro;

	@OneToOne(optional = false, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "frequencia_id", nullable = false, unique = true)
	private Frequencia frequencia;

	protected Justificativa() {}

	public Justificativa(String motivo, Frequencia frequencia) {
		this.motivo = motivo;
		this.frequencia = frequencia;
		this.dataRegistro = LocalDate.now();
	}

	public Long getId() { return id; }

	public String getMotivo() { return motivo; }
	public void setMotivo(String motivo) { this.motivo = motivo; }

	public LocalDate getDataRegistro() { return dataRegistro; }

	public Frequencia getFrequencia() { return frequencia; }
	public void setFrequencia(Frequencia frequencia) { this.frequencia = frequencia; }
}
