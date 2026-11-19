package br.com.logitrack.repository.specification;

import br.com.logitrack.model.entity.Veiculo;
import br.com.logitrack.model.enums.TipoVeiculo;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

/**
 * Filtros dinâmicos de Veiculo.
 */
public final class VeiculoSpecs {

    private VeiculoSpecs() {
    }

    public static Specification<Veiculo> tipoIgual(TipoVeiculo tipo) {
        if (tipo == null) return null;
        return (root, query, cb) -> cb.equal(root.get("tipo"), tipo);
    }

    public static Specification<Veiculo> marcaContem(String marca) {
        if (marca == null || marca.isBlank()) return null;
        return (root, query, cb) -> cb.like(cb.lower(root.get("marca")), "%" + marca.trim().toLowerCase() + "%");
    }

    public static Specification<Veiculo> ativoIgual(Boolean ativo) {
        if (ativo == null) return null;
        return (root, query, cb) -> cb.equal(root.get("ativo"), ativo);
    }

    public static Specification<Veiculo> capacidadeMinima(BigDecimal capacidadeMin) {
        if (capacidadeMin == null) return null;
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("capacidadeKg"), capacidadeMin);
    }
}
