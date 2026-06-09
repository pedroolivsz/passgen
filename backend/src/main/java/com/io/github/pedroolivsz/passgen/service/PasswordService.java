package com.io.github.pedroolivsz.passgen.service;

import com.io.github.pedroolivsz.passgen.DTOs.PasswordRequest;
import com.io.github.pedroolivsz.passgen.DTOs.PasswordResponse;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * Serviço responsável pela lógica de negócio de geração de senhas seguras.
 * <p>
 * Utiliza o {@link SecureRandom} para garantir a aleatoriedade criptográfica e
 * aplica filtros personalizados com base nos parâmetros fornecidos no request.
 * </p>
 */
@Service
public class PasswordService {
    /**
     * Gerador de números pseudo-aleatórios criptograficamente forte.
     * Usado em vez do {@code Random} comum para garantir senhas seguras contra ataques de previsão.
     */
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Constrói o alfabeto (pool de caracteres) com base nas preferências enviadas pelo usuário.
     * Também lida com a exclusão de caracteres ambíguos caso solicitado.
     *
     * @param request DTO com as regras de inclusão (letras, números, símbolos)
     * @return Uma String contendo todos os caracteres válidos para a geração da senha
     */
    private String buildAlphabet(PasswordRequest request) {
        StringBuilder alphabet = new StringBuilder();

        if(request.uppercase()) alphabet.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        if(request.lowercase()) alphabet.append("abcdefghijklmnopqrstuvwxyz");
        if(request.numbers()) alphabet.append("0123456789");
        if(request.symbols()) alphabet.append("!@#$%^&*()_+-=[]{}");

        String pool = alphabet.toString();

        // Remove caracteres visualmente parecidos (ex: o número 0 e a letra O) se ativo
        if(request.excludeAmbiguous()) {
            pool = pool.replaceAll("[l1IO0oB8S5Z2]", "");
        }
        return pool;
    }

    /**
     * Gera uma única senha aleatória a partir do alfabeto disponível e do tamanho solicitado.
     *
     * @param alphabet Pool de caracteres permitidos
     * @param length O tamanho exato que a senha deve ter
     * @return A senha gerada como uma String
     */
    private String generateSinglePassword(String alphabet, int length) {
        StringBuilder password = new StringBuilder(length);

        for(int i = 0; i < length; i++) {
            int randomIndex = RANDOM.nextInt(alphabet.length());
            password.append(alphabet.charAt(randomIndex));
        }

        return password.toString();
    }

    /**
     * Executa a geração em lote das senhas requisitadas.
     * <p>
     * Valida se pelo menos uma categoria de caractere foi selecionada e aplica valores
     * padrão caso o tamanho ou a quantidade não tenham sido especificados.
     * </p>
     *
     * @param request Parâmetros de configuração enviados pelo cliente
     * @return {@link PasswordResponse} contendo a lista de senhas geradas e o tamanho delas
     * @throws IllegalArgumentException Se o alfabeto resultante for vazio (nenhum tipo de caractere selecionado)
     */
    public PasswordResponse generate(PasswordRequest request) {
        String alphabet = buildAlphabet(request);

        // Dispara o erro capturado pelo GlobalExceptionHandler
        if(alphabet.isEmpty()) {
            throw new IllegalArgumentException("Pelo menos uma categoria de caracteres deve ser selecionada.");
        }

        List<String> passwords = new ArrayList<>();
        int quantity = request.quantity() != null ? request.quantity() : 1;
        int passwordLength = request.length() != null ? request.length() : 16;

        for(int i = 0; i < quantity; i++) {
            passwords.add(generateSinglePassword(alphabet, passwordLength));
        }

        return new PasswordResponse(passwords, passwordLength);
    }
}
