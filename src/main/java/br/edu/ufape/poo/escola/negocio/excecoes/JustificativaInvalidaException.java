package br.edu.ufape.poo.escola.negocio.excecoes;

/**
 * Lançada pela {@code EscolaFacade} quando se tenta justificar uma
 * frequência que não está com status AUSENTE. Satisfaz o requisito de
 * "exceção em fachada".
 */
public class JustificativaInvalidaException extends RuntimeException {

    public JustificativaInvalidaException(String mensagem) {
        super(mensagem);
    }
}
