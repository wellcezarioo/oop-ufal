// UsuarioNaoPodeSerPaqueraDeSiMesmoException.java
package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioNaoPodeSerPaqueraDeSiMesmoException extends RuntimeException {
    public UsuarioNaoPodeSerPaqueraDeSiMesmoException() {
        super("Usu�rio n�o pode ser paquera de si mesmo.");
    }
}
