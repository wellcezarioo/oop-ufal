package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exce��o lan�ada quando uma senha inv�lida � fornecida.
 * Esta exce��o ocorre quando a senha fornecida para o login ou cria��o de conta � inv�lida.
 */
public class SenhaInvalidaException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Construtor da exce��o, com uma mensagem padr�o.
     */
    public SenhaInvalidaException() {
        super("Senha inv�lida.");
    }
}
