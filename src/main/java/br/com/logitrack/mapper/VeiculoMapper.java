package br.com.logitrack.mapper;

import br.com.logitrack.dto.veiculo.VeiculoRequestDTO;
import br.com.logitrack.dto.veiculo.VeiculoResponseDTO;
import br.com.logitrack.dto.veiculo.VeiculoResumoDTO;
import br.com.logitrack.model.entity.Veiculo;

/**
 * Converte entre a entidade Veiculo e seus DTOs.
 */
public final class VeiculoMapper {

    private VeiculoMapper() {
    }

    public static void copyToEntity(VeiculoRequestDTO dto, Veiculo entity) {
        entity.setPlaca(normalizarPlaca(dto.placa()));
        entity.setModelo(dto.modelo().trim());
        entity.setMarca(dto.marca().trim());
        entity.setAno(dto.ano());
        entity.setTipo(dto.tipo());
        entity.setCapacidadeKg(dto.capacidadeKg());
        if (dto.ativo() != null) {
            entity.setAtivo(dto.ativo());
        }
    }

    public static VeiculoResponseDTO toResponse(Veiculo v) {
        return new VeiculoResponseDTO(
                v.getId(),
                v.getPlaca(),
                v.getModelo(),
                v.getMarca(),
                v.getAno(),
                v.getTipo(),
                v.getCapacidadeKg(),
                v.isAtivo(),
                v.getCriadoEm(),
                v.getAtualizadoEm()
        );
    }

    public static VeiculoResumoDTO toResumo(Veiculo v) {
        return new VeiculoResumoDTO(v.getId(), v.getPlaca(), v.getModelo(), v.getTipo());
    }

    /** "abc-1234" -> "ABC1234" */
    public static String normalizarPlaca(String placa) {
        return placa == null ? null : placa.replace("-", "").trim().toUpperCase();
    }
}
