// UsuarioJaEstaPaqueraException.java
package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioJaEstaPaqueraException extends RuntimeException {
    public UsuarioJaEstaPaqueraException() {
        super("Usu�rio j� est� adicionado como paquera.");
    }
}
