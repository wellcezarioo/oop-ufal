package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exce��o lan�ada quando um usu�rio j� est� adicionado como amigo.
 * Esta exce��o ocorre quando h� uma tentativa de adicionar um usu�rio que j� foi adicionado � lista de amigos.
 */
public class UsuarioJaEstaAdicionadoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Construtor da exce��o, com uma mensagem padr�o.
     */
    public UsuarioJaEstaAdicionadoException() {
        super("Usu�rio j� est� adicionado como amigo.");
    }
}
