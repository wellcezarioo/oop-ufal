// UsuarioNaoPodeSerInimigoDeSiMesmoException.java
package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioNaoPodeSerInimigoDeSiMesmoException extends RuntimeException {
    public UsuarioNaoPodeSerInimigoDeSiMesmoException() {
        super("Usu�rio n�o pode ser inimigo de si mesmo.");
    }
}
