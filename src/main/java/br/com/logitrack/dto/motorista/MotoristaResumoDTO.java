package br.com.logitrack.dto.motorista;

import br.com.logitrack.model.enums.CategoriaCnh;

/**
 * Versão resumida do motorista, usada dentro do DTO de saída da entrega.
 */
public record MotoristaResumoDTO(Long id, String nome, CategoriaCnh categoriaCnh) {
}
