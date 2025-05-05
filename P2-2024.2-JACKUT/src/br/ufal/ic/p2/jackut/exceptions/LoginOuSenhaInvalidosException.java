package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exce��o lan�ada quando o login ou a senha fornecidos s�o inv�lidos.
 * Essa exce��o � usada quando um usu�rio tenta fazer login com credenciais incorretas.
 */
public class LoginOuSenhaInvalidosException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Construtor da exce��o, com uma mensagem padr�o.
     */
    public LoginOuSenhaInvalidosException() {
        super("Login ou senha inv�lidos.");
    }
}
