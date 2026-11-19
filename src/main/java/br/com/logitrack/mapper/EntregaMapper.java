package br.com.logitrack.mapper;

import br.com.logitrack.dto.entrega.EntregaRequestDTO;
import br.com.logitrack.dto.entrega.EntregaResponseDTO;
import br.com.logitrack.dto.entrega.EntregaUpdateDTO;
import br.com.logitrack.model.entity.Entrega;

/**
 * Converte entre a entidade Entrega e seus DTOs.
 * Motorista e veículo são resolvidos no service (precisam do banco).
 */
public final class EntregaMapper {

    private EntregaMapper() {
    }

    public static Entrega toEntity(EntregaRequestDTO dto) {
        Entrega e = new Entrega();
        e.setDescricao(dto.descricao().trim());
        e.setDestinatarioNome(dto.destinatarioNome().trim());
        e.setEnderecoDestino(dto.enderecoDestino().trim());
        e.setCidadeDestino(dto.cidadeDestino().trim());
        e.setUfDestino(dto.ufDestino().toUpperCase());
        e.setPesoKg(dto.pesoKg());
        e.setValorFrete(dto.valorFrete());
        e.setDataPrevista(dto.dataPrevista());
        return e;
    }

    public static void copyToEntity(EntregaUpdateDTO dto, Entrega e) {
        e.setDescricao(dto.descricao().trim());
        e.setDestinatarioNome(dto.destinatarioNome().trim());
        e.setEnderecoDestino(dto.enderecoDestino().trim());
        e.setCidadeDestino(dto.cidadeDestino().trim());
        e.setUfDestino(dto.ufDestino().toUpperCase());
        e.setPesoKg(dto.pesoKg());
        e.setValorFrete(dto.valorFrete());
        e.setDataPrevista(dto.dataPrevista());
    }

    public static EntregaResponseDTO toResponse(Entrega e) {
        return new EntregaResponseDTO(
                e.getId(),
                e.getCodigoRastreio(),
                e.getDescricao(),
                e.getDestinatarioNome(),
                e.getEnderecoDestino(),
                e.getCidadeDestino(),
                e.getUfDestino(),
                e.getPesoKg(),
                e.getValorFrete(),
                e.getStatus(),
                e.getDataPrevista(),
                e.getDataEntrega(),
                MotoristaMapper.toResumo(e.getMotorista()),
                VeiculoMapper.toResumo(e.getVeiculo()),
                e.getCriadoEm(),
                e.getAtualizadoEm()
        );
    }
}
