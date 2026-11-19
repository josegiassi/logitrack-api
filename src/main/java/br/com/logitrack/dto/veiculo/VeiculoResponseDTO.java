package br.com.logitrack.dto.veiculo;

import br.com.logitrack.model.enums.TipoVeiculo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de saída de veículo.
 */
public record VeiculoResponseDTO(
        Long id,
        String placa,
        String modelo,
        String marca,
        Integer ano,
        TipoVeiculo tipo,
        BigDecimal capacidadeKg,
        boolean ativo,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm
) {
}
