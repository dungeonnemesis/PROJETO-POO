package br.edu.ufape.poo.mensageria.negocio.cadastro;

public class RegistroInexistenteException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public RegistroInexistenteException(String msg) {
		super(msg);
	}

}
