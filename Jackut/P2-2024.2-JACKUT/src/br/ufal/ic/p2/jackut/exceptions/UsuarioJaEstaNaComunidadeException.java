package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioJaEstaNaComunidadeException extends RuntimeException {
    public UsuarioJaEstaNaComunidadeException() {
        super("Usuario j� faz parte dessa comunidade."); // Com acento
    }
}