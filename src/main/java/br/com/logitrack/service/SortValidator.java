package br.com.logitrack.service;

import br.com.logitrack.exception.BadRequestException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Set;

/**
 * Garante que o cliente só ordene por campos permitidos (whitelist),
 * devolvendo 400 em vez de deixar a consulta falhar no banco.
 */
public final class SortValidator {

    private SortValidator() {
    }

    public static void validar(Pageable pageable, Set<String> camposPermitidos) {
        for (Sort.Order order : pageable.getSort()) {
            if (!camposPermitidos.contains(order.getProperty())) {
                throw new BadRequestException("Não é possível ordenar por '" + order.getProperty()
                        + "'. Campos permitidos: " + camposPermitidos);
            }
        }
    }
}
