package br.ufal.ic.p2.jackut.exceptions;

public class ComunidadeNaoExisteException extends RuntimeException {
    public ComunidadeNaoExisteException() {
        super("Comunidade n�o existe.");
    }
}