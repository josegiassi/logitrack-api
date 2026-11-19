package br.com.logitrack.dto.motorista;

import br.com.logitrack.model.enums.CategoriaCnh;

import java.time.LocalDateTime;

/**
 * DTO de saída de motorista. O CPF é devolvido mascarado (dado sensível – LGPD).
 */
public record MotoristaResponseDTO(
        Long id,
        String nome,
        String cpf,
        String cnh,
        CategoriaCnh categoriaCnh,
        String telefone,
        String email,
        boolean ativo,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm
) {
}
