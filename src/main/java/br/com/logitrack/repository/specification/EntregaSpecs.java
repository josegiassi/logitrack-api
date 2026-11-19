package br.com.logitrack.repository.specification;

import br.com.logitrack.model.entity.Entrega;
import br.com.logitrack.model.enums.StatusEntrega;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

/**
 * Filtros dinâmicos de Entrega.
 */
public final class EntregaSpecs {

    private EntregaSpecs() {
    }

    public static Specification<Entrega> statusIgual(StatusEntrega status) {
        if (status == null) return null;
        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }

    public static Specification<Entrega> motoristaIgual(Long motoristaId) {
        if (motoristaId == null) return null;
        return (root, query, cb) -> cb.equal(root.get("motorista").get("id"), motoristaId);
    }

    public static Specification<Entrega> veiculoIgual(Long veiculoId) {
        if (veiculoId == null) return null;
        return (root, query, cb) -> cb.equal(root.get("veiculo").get("id"), veiculoId);
    }

    public static Specification<Entrega> cidadeContem(String cidade) {
        if (cidade == null || cidade.isBlank()) return null;
        return (root, query, cb) -> cb.like(cb.lower(root.get("cidadeDestino")), "%" + cidade.trim().toLowerCase() + "%");
    }

    public static Specification<Entrega> ufIgual(String uf) {
        if (uf == null || uf.isBlank()) return null;
        return (root, query, cb) -> cb.equal(root.get("ufDestino"), uf.trim().toUpperCase());
    }

    public static Specification<Entrega> dataPrevistaDesde(LocalDate inicio) {
        if (inicio == null) return null;
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("dataPrevista"), inicio);
    }

    public static Specification<Entrega> dataPrevistaAte(LocalDate fim) {
        if (fim == null) return null;
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("dataPrevista"), fim);
    }
}
