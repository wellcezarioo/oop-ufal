package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exce��o lan�ada quando um usu�rio tenta adicionar a si mesmo como amigo.
 * Esta exce��o ocorre quando um usu�rio tenta enviar um convite de amizade para si pr�prio, o que n�o � permitido.
 */
public class UsuarioNaoPodeAdicionarASiMesmoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Construtor da exce��o, com uma mensagem padr�o.
     */
    public UsuarioNaoPodeAdicionarASiMesmoException() {
        super("Usu�rio n�o pode adicionar a si mesmo como amigo.");
    }
}
