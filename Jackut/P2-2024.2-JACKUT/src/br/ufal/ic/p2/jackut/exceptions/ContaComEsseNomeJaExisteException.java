package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exce��o lan�ada quando h� uma tentativa de criar uma conta com um nome de usu�rio j� existente.
 * Esta exce��o � usada quando o login fornecido j� est� em uso no sistema.
 */
public class ContaComEsseNomeJaExisteException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Construtor da exce��o, com uma mensagem padr�o.
     */
    public ContaComEsseNomeJaExisteException() {
        super("Conta com esse nome j� existe.");
    }
}
