package com.io.github.pedroolivsz.passgen.DTOs;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record PasswordRequest(
        @Min(value = 8)
        @Max(value = 128)
        Integer length,
        boolean uppercase,
        boolean lowercase,
        boolean numbers,
        boolean symbols,
        @Min(1)
        @Max(20)
        Integer quantity,
        boolean excludeAmbiguous
) {
    public PasswordRequest() {
        this(16, true, true, true, false, 1, false);
    }
}
