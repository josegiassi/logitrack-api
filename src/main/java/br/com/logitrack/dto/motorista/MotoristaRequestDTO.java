package br.com.logitrack.dto.motorista;

import br.com.logitrack.model.enums.CategoriaCnh;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

/**
 * DTO de entrada para criar (POST) e atualizar (PUT) um motorista.
 */
public record MotoristaRequestDTO(

        @Schema(example = "Carlos Henrique Souza")
        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
        String nome,

        @Schema(example = "529.982.247-25", description = "Aceita com ou sem pontuação")
        @NotBlank(message = "O CPF é obrigatório")
        @CPF(message = "CPF inválido")
        String cpf,

        @Schema(example = "12345678901")
        @NotBlank(message = "A CNH é obrigatória")
        @Pattern(regexp = "\\d{11}", message = "A CNH deve conter exatamente 11 dígitos numéricos")
        String cnh,

        @Schema(example = "C")
        @NotNull(message = "A categoria da CNH é obrigatória (A, B, C, D ou E)")
        CategoriaCnh categoriaCnh,

        @Schema(example = "(11) 98765-4321")
        @NotBlank(message = "O telefone é obrigatório")
        @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?9?\\d{4}-?\\d{4}$",
                message = "Telefone inválido. Use o formato (11) 98765-4321")
        String telefone,

        @Schema(example = "carlos.souza@logitrack.com")
        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "E-mail inválido")
        @Size(max = 120, message = "O e-mail deve ter no máximo 120 caracteres")
        String email,

        @Schema(example = "true", description = "Opcional. Padrão: true na criação; mantém o valor atual na atualização")
        Boolean ativo
) {
}
