package br.com.logitrack.exception;

/** Recurso não encontrado -> HTTP 404. */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String recurso, Object id) {
        super(recurso + " com id " + id + " não encontrado(a)");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
