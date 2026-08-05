package br.edu.ufape.poo.mensageria.negocio.basica;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class MensagemGrupo extends Mensagem{
	@ManyToOne
	public Grupo destinatario;

	protected MensagemGrupo() {
		super();
	}

	public MensagemGrupo(String titulo, String texto, Usuario remetente, Grupo destinatario) {
		super(titulo, texto, remetente);
		this.destinatario = destinatario;
	}

	public Grupo getDestinatario() {
		return destinatario;
	}
}
