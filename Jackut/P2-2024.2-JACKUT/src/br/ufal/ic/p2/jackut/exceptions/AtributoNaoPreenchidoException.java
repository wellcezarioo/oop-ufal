package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exce��o lan�ada quando um atributo requerido n�o foi preenchido.
 * Esta exce��o � utilizada quando o usu�rio tenta acessar um atributo que n�o foi definido.
 */
public class AtributoNaoPreenchidoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Construtor da exce��o, com uma mensagem padr�o.
     */
    public AtributoNaoPreenchidoException() {
        super("Atributo n�o preenchido.");
    }
}
