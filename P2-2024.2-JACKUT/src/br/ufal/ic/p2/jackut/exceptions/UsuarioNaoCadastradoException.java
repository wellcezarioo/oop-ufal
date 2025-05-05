package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exce��o lan�ada quando um usu�rio n�o est� cadastrado no sistema.
 * Essa exce��o � usada quando um usu�rio tenta realizar uma a��o (como login ou edi��o de perfil) e o login fornecido n�o corresponde a um usu�rio registrado.
 */
public class UsuarioNaoCadastradoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Construtor da exce��o, com uma mensagem padr�o.
     */
    public UsuarioNaoCadastradoException() {
        super("Usu�rio n�o cadastrado.");
    }
}
