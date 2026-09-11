package br.edu.ufape.poo.escola.negocio.excecoes;

/**
 * Lançada pela própria classe básica {@code Frequencia} quando os dados
 * recebidos violam uma regra elementar do domínio (data nula, data futura
 * ou status ausente). Satisfaz o requisito de "exceção em classe básica
 * de negócio".
 */
public class DataFrequenciaInvalidaException extends RuntimeException {

    public DataFrequenciaInvalidaException(String mensagem) {
        super(mensagem);
    }
}
