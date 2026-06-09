package com.io.github.pedroolivsz.passgen.controller;

import com.io.github.pedroolivsz.passgen.DTOs.PasswordRequest;
import com.io.github.pedroolivsz.passgen.DTOs.PasswordResponse;
import com.io.github.pedroolivsz.passgen.service.PasswordService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/passwords")
public class PasswordController {
    private final PasswordService service;

    public PasswordController(PasswordService service) {
        this.service = service;
    }

    @PostMapping("/generate")
    public ResponseEntity<PasswordResponse> generate(@RequestBody @Valid PasswordRequest request) {
        return ResponseEntity.ok(service.generate(request));
    }
}
