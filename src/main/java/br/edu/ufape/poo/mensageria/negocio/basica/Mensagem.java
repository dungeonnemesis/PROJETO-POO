package br.edu.ufape.poo.mensageria.negocio.basica;

import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Mensagem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private String texto;
	private Date dataHora;
	
	@ManyToOne
	private Usuario remetente;

	protected Mensagem() {
		super();
	}

	public Mensagem(String titulo, String texto, Usuario remetente) {
		super();
		this.titulo = titulo;
		this.texto = texto;
		this.remetente = remetente;
		this.dataHora = new Date();
	}

	public Long getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getTexto() {
		return texto;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public Usuario getRemetente() {
		return remetente;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, remetente, texto, titulo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mensagem other = (Mensagem) obj;
		return Objects.equals(id, other.id) && Objects.equals(remetente, other.remetente)
				&& Objects.equals(texto, other.texto) && Objects.equals(titulo, other.titulo);
	}
}
