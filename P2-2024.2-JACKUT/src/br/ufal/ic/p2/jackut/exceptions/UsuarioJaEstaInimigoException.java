// UsuarioJaEstaInimigoException.java
package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioJaEstaInimigoException extends RuntimeException {
    public UsuarioJaEstaInimigoException() {
        super("Usu�rio j� est� adicionado como inimigo.");
    }
}
