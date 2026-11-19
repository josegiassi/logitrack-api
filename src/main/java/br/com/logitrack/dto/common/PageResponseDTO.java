package br.com.logitrack.dto.common;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

/**
 * Envelope padrão das respostas paginadas (GET ALL).
 * Evita expor a estrutura interna do Page do Spring e mantém o contrato estável.
 */
public record PageResponseDTO<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean first,
        boolean last,
        String sort
) {

    public static <E, T> PageResponseDTO<T> from(Page<E> page, Function<E, T> mapper) {
        String sort = page.getSort().isSorted() ? page.getSort().toString().replace(": ", ",") : "UNSORTED";
        return new PageResponseDTO<>(
                page.getContent().stream().map(mapper).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast(),
                sort
        );
    }
}
