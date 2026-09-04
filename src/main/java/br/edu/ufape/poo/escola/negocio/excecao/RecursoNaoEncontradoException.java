package br.edu.ufape.poo.escola.negocio.excecao;

public class RecursoNaoEncontradoException extends RuntimeException {
	public RecursoNaoEncontradoException(String recurso, Long id) {
		super(recurso + " de id " + id + " nao encontrado(a)");
	}
}
