package com.io.github.pedroolivsz.passgen.controller;

import com.io.github.pedroolivsz.passgen.DTOs.PasswordRequest;
import com.io.github.pedroolivsz.passgen.DTOs.PasswordResponse;
import com.io.github.pedroolivsz.passgen.service.PasswordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/passwords")
@Tag(name = "Passwords", description = "Gerador de senhas aleatórias")
public class PasswordController {
    private final PasswordService service;

    public PasswordController(PasswordService service) {
        this.service = service;
    }

    @Operation(
            summary = "Gerar senhas",
            description = "Gera uma ou mais senhas de acordo com os parametros informados"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Senhas geradas com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
    })
    @PostMapping("/generate")
    public ResponseEntity<PasswordResponse> generate(@RequestBody @Valid PasswordRequest request) {
        return ResponseEntity.ok(service.generate(request));
    }
}
