package br.ufal.ic.p2.jackut.exceptions;

public class NaoHaMensagensException extends RuntimeException {
    public NaoHaMensagensException() {
        super("N�o h� mensagens.");
    }
}
