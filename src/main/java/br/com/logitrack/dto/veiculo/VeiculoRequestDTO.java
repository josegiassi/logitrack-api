package br.com.logitrack.dto.veiculo;

import br.com.logitrack.model.enums.TipoVeiculo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * DTO de entrada para criar (POST) e atualizar (PUT) um veículo.
 */
public record VeiculoRequestDTO(

        @Schema(example = "BRA2E19", description = "Padrão antigo (ABC-1234) ou Mercosul (ABC1D23)")
        @NotBlank(message = "A placa é obrigatória")
        @Pattern(regexp = "^[A-Za-z]{3}-?[0-9][A-Za-z0-9][0-9]{2}$",
                message = "Placa inválida. Use o padrão ABC-1234 ou ABC1D23")
        String placa,

        @Schema(example = "Accelo 1016")
        @NotBlank(message = "O modelo é obrigatório")
        @Size(max = 60, message = "O modelo deve ter no máximo 60 caracteres")
        String modelo,

        @Schema(example = "Mercedes-Benz")
        @NotBlank(message = "A marca é obrigatória")
        @Size(max = 60, message = "A marca deve ter no máximo 60 caracteres")
        String marca,

        @Schema(example = "2022")
        @NotNull(message = "O ano é obrigatório")
        @Min(value = 1980, message = "O ano deve ser maior ou igual a 1980")
        Integer ano,

        @Schema(example = "CAMINHAO")
        @NotNull(message = "O tipo é obrigatório (MOTO, CARRO, VAN ou CAMINHAO)")
        TipoVeiculo tipo,

        @Schema(example = "5000.00")
        @NotNull(message = "A capacidade em kg é obrigatória")
        @Positive(message = "A capacidade deve ser maior que zero")
        @Digits(integer = 8, fraction = 2, message = "Capacidade com no máximo 8 dígitos inteiros e 2 decimais")
        BigDecimal capacidadeKg,

        @Schema(example = "true", description = "Opcional. Padrão: true na criação; mantém o valor atual na atualização")
        Boolean ativo
) {
}
