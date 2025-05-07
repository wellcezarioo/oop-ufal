package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exce��o lan�ada quando n�o h� recados para serem lidos.
 * Esta exce��o � utilizada quando o usu�rio tenta ler um recado, mas sua fila de recados est� vazia.
 */
public class NaoHaRecadosException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Construtor da exce��o, com uma mensagem padr�o.
     */
    public NaoHaRecadosException() {
        super("N�o h� recados.");
    }
}
