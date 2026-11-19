package br.com.logitrack.model.enums;

/**
 * Ciclo de vida de uma entrega.
 * PENDENTE -> EM_TRANSITO -> ENTREGUE
 * PENDENTE/EM_TRANSITO -> CANCELADA
 */
public enum StatusEntrega {
    PENDENTE,
    EM_TRANSITO,
    ENTREGUE,
    CANCELADA;

    /** Entregas finalizadas não podem mais ser alteradas. */
    public boolean isFinal() {
        return this == ENTREGUE || this == CANCELADA;
    }
}
