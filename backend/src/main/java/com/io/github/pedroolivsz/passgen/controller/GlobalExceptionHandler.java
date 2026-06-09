package com.io.github.pedroolivsz.passgen.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.List;

/**
 * Controlador global para captura e tratamento de exceções da API.
 * Centraliza os erros e os expõe em um formato padronizado para o cliente
 */
@RestControllerAdvice
@Schema(description = "Manipulador global de exceções da API")
public class GlobalExceptionHandler {
    /**
     * Captura de erros de validação de argumentos de métodos (ex: restrições do Jakarta Validation).
     * @param ex Exceção contendo os detalhes dos campos inválidos.
     * @return Resposta HTTP 400 (Bad Request) com a lista de erros de validação formatada.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .toList();

        return ResponseEntity.badRequest().body(new ErrorResponse(errors));
    }

    /**
     * Captura exceções de argumentos ilegais ou inadequados passados para os métodos do sistema.
     *
     * @param ex Exceção contendo a mensagem de erro original.
     * @return Resposta HTTP 400 (Bad Request) com a mensagem de erro.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(List.of(ex.getMessage())));
    }

    /**
     * DTO de resposta utilizada para padronizar o retorno de erros da API.
     *
     * @param errors Lista de mensagens de erro detalhadas.
     */
    @Schema(description = "Estrutura padrão de resposta para erros da API.")
    public record ErrorResponse(
            @Schema(
                    description = "Lista contendo as mensagens de erro detalhadas",
                    example = "[\"length: O tamanho mínimo é de 8 caracteres\", \"quantity: Máximo de 20 senhas por requisição\"]"
            )
            List<String> errors
    ) {}
}

