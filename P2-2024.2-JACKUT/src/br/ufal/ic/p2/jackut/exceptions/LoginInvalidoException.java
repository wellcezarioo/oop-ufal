package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exce��o lan�ada quando um login inv�lido � fornecido.
 * Esta exce��o ocorre quando o login fornecido para a cria��o de conta ou para o acesso ao sistema � inv�lido.
 */
public class LoginInvalidoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Construtor da exce��o, com uma mensagem padr�o.
     */
    public LoginInvalidoException() {
        super("Login inv�lido.");
    }
}
