package br.com.logitrack.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Formato padrão de todas as respostas de erro da API.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ErrorResponseDTO(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        List<FieldErrorDTO> fieldErrors
) {

    public record FieldErrorDTO(String field, String message) {
    }
}
