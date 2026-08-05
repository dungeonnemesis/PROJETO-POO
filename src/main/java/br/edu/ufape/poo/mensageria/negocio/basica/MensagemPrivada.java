package br.edu.ufape.poo.mensageria.negocio.basica;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class MensagemPrivada extends Mensagem{
	@ManyToOne
	private Usuario destinatario;

	protected MensagemPrivada() {
		super();
	}

	protected MensagemPrivada(String titulo, String texto, Usuario remetente, Usuario destinatario) {
		super(titulo, texto, remetente);
		this.destinatario = destinatario;
	}

	public Usuario getDestinatario() {
		return destinatario;
	}
}
