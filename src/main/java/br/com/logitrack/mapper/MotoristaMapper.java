package br.com.logitrack.mapper;

import br.com.logitrack.dto.motorista.MotoristaRequestDTO;
import br.com.logitrack.dto.motorista.MotoristaResponseDTO;
import br.com.logitrack.dto.motorista.MotoristaResumoDTO;
import br.com.logitrack.model.entity.Motorista;

/**
 * Converte entre a entidade Motorista e seus DTOs.
 */
public final class MotoristaMapper {

    private MotoristaMapper() {
    }

    /** Copia os dados do DTO para a entidade (usado no create e no update). */
    public static void copyToEntity(MotoristaRequestDTO dto, Motorista entity) {
        entity.setNome(dto.nome().trim());
        entity.setCpf(somenteDigitos(dto.cpf()));
        entity.setCnh(dto.cnh());
        entity.setCategoriaCnh(dto.categoriaCnh());
        entity.setTelefone(somenteDigitos(dto.telefone()));
        entity.setEmail(dto.email().trim().toLowerCase());
        if (dto.ativo() != null) {
            entity.setAtivo(dto.ativo());
        }
    }

    public static MotoristaResponseDTO toResponse(Motorista m) {
        return new MotoristaResponseDTO(
                m.getId(),
                m.getNome(),
                mascararCpf(m.getCpf()),
                m.getCnh(),
                m.getCategoriaCnh(),
                m.getTelefone(),
                m.getEmail(),
                m.isAtivo(),
                m.getCriadoEm(),
                m.getAtualizadoEm()
        );
    }

    public static MotoristaResumoDTO toResumo(Motorista m) {
        return new MotoristaResumoDTO(m.getId(), m.getNome(), m.getCategoriaCnh());
    }

    public static String somenteDigitos(String valor) {
        return valor == null ? null : valor.replaceAll("\\D", "");
    }

    /** 52998224725 -> ***.982.247-** */
    static String mascararCpf(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            return cpf;
        }
        return "***." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-**";
    }
}
