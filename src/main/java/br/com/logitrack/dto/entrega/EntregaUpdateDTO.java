package br.com.logitrack.dto.entrega;

import br.com.logitrack.model.enums.StatusEntrega;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO de entrada para atualizar (PUT) uma entrega.
 * Diferente do DTO de criação: permite alterar o status e não exige data futura
 * (uma entrega atrasada pode ser atualizada).
 */
public record EntregaUpdateDTO(

        @Schema(example = "10 caixas de peças automotivas")
        @NotBlank(message = "A descrição é obrigatória")
        @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
        String descricao,

        @Schema(example = "Auto Peças Paulista LTDA")
        @NotBlank(message = "O nome do destinatário é obrigatório")
        @Size(max = 100, message = "O nome do destinatário deve ter no máximo 100 caracteres")
        String destinatarioNome,

        @Schema(example = "Av. Paulista, 1000 - Bela Vista")
        @NotBlank(message = "O endereço de destino é obrigatório")
        @Size(max = 200, message = "O endereço deve ter no máximo 200 caracteres")
        String enderecoDestino,

        @Schema(example = "São Paulo")
        @NotBlank(message = "A cidade de destino é obrigatória")
        @Size(max = 80, message = "A cidade deve ter no máximo 80 caracteres")
        String cidadeDestino,

        @Schema(example = "SP")
        @NotBlank(message = "A UF de destino é obrigatória")
        @Pattern(regexp = "^[A-Za-z]{2}$", message = "A UF deve ter 2 letras (ex.: SP)")
        String ufDestino,

        @Schema(example = "350.5")
        @NotNull(message = "O peso em kg é obrigatório")
        @Positive(message = "O peso deve ser maior que zero")
        @Digits(integer = 8, fraction = 2, message = "Peso com no máximo 8 dígitos inteiros e 2 decimais")
        BigDecimal pesoKg,

        @Schema(example = "480.00")
        @NotNull(message = "O valor do frete é obrigatório")
        @PositiveOrZero(message = "O valor do frete não pode ser negativo")
        @Digits(integer = 8, fraction = 2, message = "Valor com no máximo 8 dígitos inteiros e 2 decimais")
        BigDecimal valorFrete,

        @Schema(example = "2026-12-01")
        @NotNull(message = "A data prevista é obrigatória")
        LocalDate dataPrevista,

        @Schema(example = "1")
        @NotNull(message = "O motoristaId é obrigatório")
        @Positive(message = "O motoristaId deve ser positivo")
        Long motoristaId,

        @Schema(example = "1")
        @NotNull(message = "O veiculoId é obrigatório")
        @Positive(message = "O veiculoId deve ser positivo")
        Long veiculoId,

        @Schema(example = "EM_TRANSITO")
        @NotNull(message = "O status é obrigatório (PENDENTE, EM_TRANSITO, ENTREGUE ou CANCELADA)")
        StatusEntrega status
) {
}
