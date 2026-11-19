package br.com.logitrack.exception;

/** Requisição inválida (ex.: parâmetro de ordenação ou filtro inválido) -> HTTP 400. */
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
