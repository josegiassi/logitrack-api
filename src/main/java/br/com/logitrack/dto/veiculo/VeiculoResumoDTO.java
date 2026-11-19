package br.com.logitrack.dto.veiculo;

import br.com.logitrack.model.enums.TipoVeiculo;

/**
 * Versão resumida do veículo, usada dentro do DTO de saída da entrega.
 */
public record VeiculoResumoDTO(Long id, String placa, String modelo, TipoVeiculo tipo) {
}
