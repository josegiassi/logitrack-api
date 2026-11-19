package br.com.logitrack.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.Objects;

/**
 * Combina vários filtros com AND, ignorando os que não foram informados (null).
 */
public final class Specs {

    private Specs() {
    }

    @SafeVarargs
    public static <T> Specification<T> allOf(Specification<T>... specs) {
        return Specification.allOf(Arrays.stream(specs).filter(Objects::nonNull).toList());
    }
}
