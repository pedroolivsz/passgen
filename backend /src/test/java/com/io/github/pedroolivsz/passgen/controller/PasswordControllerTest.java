package com.io.github.pedroolivsz.passgen.controller;

import com.io.github.pedroolivsz.passgen.DTOs.PasswordRequest;
import com.io.github.pedroolivsz.passgen.DTOs.PasswordResponse;
import com.io.github.pedroolivsz.passgen.service.PasswordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({PasswordController.class, GlobalExceptionHandler.class})
public class PasswordControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private PasswordService passwordService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void generate_withValidRequest_returns200WithPasswords() throws Exception {
        PasswordRequest request = new PasswordRequest(
                22, true, true, true, true, 2, false
        );

        PasswordResponse mockResponse = new PasswordResponse(
                List.of("Ab1!", "Cd2@"), 22
        );

        when(passwordService.generate(any(PasswordRequest.class))).thenReturn(mockResponse);

        mockMvc.perform(post("/api/v1/passwords/generate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.passwords").isArray())
                .andExpect(jsonPath("$.passwords.length()").value(2))
                .andExpect(jsonPath("$.passwordLength").value(22));
    }

    @Test
    void generate_withMissingBody_returns400() throws Exception {
        mockMvc.perform(post("/api/v1/passwords/generate")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void generate_whenServiceThrowsIllegalArgument_returns400() throws Exception {
        PasswordRequest request = new PasswordRequest(
                16, false, false, false, false, 1, false
        );

        when(passwordService.generate(any(PasswordRequest.class)))
                .thenThrow(new IllegalArgumentException("Pelo menos uma categoria deve ser selecionada."));

        mockMvc.perform(post("/api/v1/passwords/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]")
                        .value("Pelo menos uma categoria deve ser selecionada."));
    }
}
