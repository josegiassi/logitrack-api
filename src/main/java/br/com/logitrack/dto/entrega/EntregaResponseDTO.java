package br.com.logitrack.dto.entrega;

import br.com.logitrack.dto.motorista.MotoristaResumoDTO;
import br.com.logitrack.dto.veiculo.VeiculoResumoDTO;
import br.com.logitrack.model.enums.StatusEntrega;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO de saída de entrega. Em vez de expor as entidades relacionadas inteiras,
 * devolve resumos do motorista e do veículo (evita ciclos e vazamento de dados).
 */
public record EntregaResponseDTO(
        Long id,
        String codigoRastreio,
        String descricao,
        String destinatarioNome,
        String enderecoDestino,
        String cidadeDestino,
        String ufDestino,
        BigDecimal pesoKg,
        BigDecimal valorFrete,
        StatusEntrega status,
        LocalDate dataPrevista,
        LocalDateTime dataEntrega,
        MotoristaResumoDTO motorista,
        VeiculoResumoDTO veiculo,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm
) {
}
