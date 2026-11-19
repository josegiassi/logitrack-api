package br.com.logitrack.model.enums;

import java.util.EnumSet;
import java.util.Set;

/**
 * Tipos de veículo da frota e as categorias de CNH que podem conduzi-los.
 */
public enum TipoVeiculo {
    MOTO(EnumSet.of(CategoriaCnh.A)),
    CARRO(EnumSet.of(CategoriaCnh.B, CategoriaCnh.C, CategoriaCnh.D, CategoriaCnh.E)),
    VAN(EnumSet.of(CategoriaCnh.B, CategoriaCnh.C, CategoriaCnh.D, CategoriaCnh.E)),
    CAMINHAO(EnumSet.of(CategoriaCnh.C, CategoriaCnh.D, CategoriaCnh.E));

    private final Set<CategoriaCnh> categoriasPermitidas;

    TipoVeiculo(Set<CategoriaCnh> categoriasPermitidas) {
        this.categoriasPermitidas = categoriasPermitidas;
    }

    public boolean podeSerConduzidoPor(CategoriaCnh categoria) {
        return categoriasPermitidas.contains(categoria);
    }

    public Set<CategoriaCnh> getCategoriasPermitidas() {
        return categoriasPermitidas;
    }
}
