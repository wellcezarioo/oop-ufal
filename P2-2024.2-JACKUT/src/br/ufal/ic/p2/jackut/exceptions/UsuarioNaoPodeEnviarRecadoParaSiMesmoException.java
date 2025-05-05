package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exce��o lan�ada quando um usu�rio tenta enviar um recado para si mesmo.
 * Esta exce��o � usada quando um usu�rio tenta enviar um recado para o pr�prio login, o que n�o � permitido.
 */
public class UsuarioNaoPodeEnviarRecadoParaSiMesmoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Construtor da exce��o, com uma mensagem padr�o.
     */
    public UsuarioNaoPodeEnviarRecadoParaSiMesmoException() {
        super("Usu�rio n�o pode enviar recado para si mesmo.");
    }
}
