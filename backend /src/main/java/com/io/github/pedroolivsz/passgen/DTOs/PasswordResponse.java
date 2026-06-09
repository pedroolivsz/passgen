package com.io.github.pedroolivsz.passgen.DTOs;

import java.time.Instant;
import java.util.List;

public record PasswordResponse(
        List<String> passwords,
        Integer passwordLength,
        Instant generateAt
) {
    public PasswordResponse(List<String> passwords, Integer passwordLength) {
        this(passwords, passwordLength, Instant.now());
    }
}
