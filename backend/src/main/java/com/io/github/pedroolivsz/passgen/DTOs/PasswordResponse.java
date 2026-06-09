package com.io.github.pedroolivsz.passgen.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.List;

@Schema(description = "Resposta com senahs geradas")
public record PasswordResponse(
        @Schema(description = "Lista com senhas geradas")
        List<String> passwords,
        @Schema(description = "Tamanho de cada senha")
        int passwordLength,
        @Schema(description = "Momento da geração de senhas")
        Instant generatedAt
) {
    public PasswordResponse(List<String> passwords, int passwordLength) {
        this(passwords, passwordLength, Instant.now());
    }
}
