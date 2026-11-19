package br.com.logitrack.exception;

/** Conflito com o estado atual do recurso (duplicidade, vínculo existente) -> HTTP 409. */
public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }
}
