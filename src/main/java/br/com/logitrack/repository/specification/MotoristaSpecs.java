package br.com.logitrack.repository.specification;

import br.com.logitrack.model.entity.Motorista;
import br.com.logitrack.model.enums.CategoriaCnh;
import org.springframework.data.jpa.domain.Specification;

/**
 * Filtros dinâmicos de Motorista. Cada método devolve null quando o filtro
 * não foi informado, e o Spring Data simplesmente o ignora.
 */
public final class MotoristaSpecs {

    private MotoristaSpecs() {
    }

    public static Specification<Motorista> nomeContem(String nome) {
        if (nome == null || nome.isBlank()) return null;
        return (root, query, cb) -> cb.like(cb.lower(root.get("nome")), "%" + nome.trim().toLowerCase() + "%");
    }

    public static Specification<Motorista> categoriaIgual(CategoriaCnh categoria) {
        if (categoria == null) return null;
        return (root, query, cb) -> cb.equal(root.get("categoriaCnh"), categoria);
    }

    public static Specification<Motorista> ativoIgual(Boolean ativo) {
        if (ativo == null) return null;
        return (root, query, cb) -> cb.equal(root.get("ativo"), ativo);
    }
}
