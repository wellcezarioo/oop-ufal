// UsuarioNaoPodeSerFaDeSiMesmoException.java
package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioNaoPodeSerFaDeSiMesmoException extends RuntimeException {
    public UsuarioNaoPodeSerFaDeSiMesmoException() {
        super("Usu�rio n�o pode ser f� de si mesmo.");
    }
}
