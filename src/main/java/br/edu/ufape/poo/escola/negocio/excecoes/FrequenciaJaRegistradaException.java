package br.edu.ufape.poo.escola.negocio.excecoes;

/**
 * Lançada pela camada de serviço ({@code FrequenciaService}) quando já
 * existe um registro de frequência para a mesma matrícula, disciplina
 * (TurmaDisciplina) e data. Satisfaz o requisito de "exceção em coleção
 * de negócio".
 */
public class FrequenciaJaRegistradaException extends RuntimeException {

    public FrequenciaJaRegistradaException(String mensagem) {
        super(mensagem);
    }
}
