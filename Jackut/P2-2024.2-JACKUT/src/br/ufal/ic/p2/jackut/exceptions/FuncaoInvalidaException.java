// FuncaoInvalidaException.java
package br.ufal.ic.p2.jackut.exceptions;

public class FuncaoInvalidaException extends RuntimeException {
    public FuncaoInvalidaException(String detalhe) {
        super("Fun��o inv�lida: " + detalhe);
    }
}
