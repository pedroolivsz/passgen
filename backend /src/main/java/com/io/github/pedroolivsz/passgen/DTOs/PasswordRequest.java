package com.io.github.pedroolivsz.passgen.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Schema(description = "Parâmetros para geração de senhas")
public record PasswordRequest(
        @Min(value = 8, message = "O tamanho mínimo é de 8 caracteres")
        @Max(value = 128, message = "O tamanho máximo é de 128 caracteres")
        @Schema(description = "Tamanho da senha", example = "16", defaultValue = "16")
        Integer length,
        @Schema(description = "Incluir letras maiúsculas", example = "true")
        boolean uppercase,
        @Schema(description = "Incluir letras minúsculas", example = "true")
        boolean lowercase,
        @Schema(description = "Incluir números", example = "true")
        boolean numbers,
        @Schema(description = "Incluir símbolos (!@#$...)", example = "false")
        boolean symbols,
        @Min(value = 1, message = "Mínimo de 1 senha por requisição")
        @Max(value = 20, message = "Máximo de 20 senhas por requisição")
        @Schema(description = "Quantidade de senhas a gerar", example = "1", defaultValue = "1")
        Integer quantity,
        @Schema(description = "Excluir caracteres ambíguos (0, O, l, 1)", example = "false")
        boolean excludeAmbiguous
) {
    public PasswordRequest() {
        this(16, true, true, true, false, 1, false);
    }
}
