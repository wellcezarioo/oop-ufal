package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exce��o lan�ada quando um usu�rio j� est� adicionado como amigo e est� aguardando a aceita��o do convite.
 * Essa exce��o � usada quando h� uma tentativa de adicionar um usu�rio que j� foi convidado, mas o convite ainda est� pendente.
 */
public class UsuarioJaEstaAdicionadoEsperandoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Construtor da exce��o, com uma mensagem padr�o.
     */
    public UsuarioJaEstaAdicionadoEsperandoException() {
        super("Usu�rio j� est� adicionado como amigo, esperando aceita��o do convite.");
    }
}
