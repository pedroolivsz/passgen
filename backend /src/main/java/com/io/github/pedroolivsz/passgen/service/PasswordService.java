package com.io.github.pedroolivsz.passgen.service;

import com.io.github.pedroolivsz.passgen.DTOs.PasswordRequest;
import com.io.github.pedroolivsz.passgen.DTOs.PasswordResponse;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Service
public class PasswordService {
    private static final SecureRandom RANDOM = new SecureRandom();

    private String buildAlphabet(PasswordRequest request) {
        StringBuilder alphabet = new StringBuilder();

        if(request.uppercase()) alphabet.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        if(request.lowercase()) alphabet.append("abcdefghijklmnopqrstuvwxyz");
        if(request.numbers()) alphabet.append("0123456789");
        if(request.symbols()) alphabet.append("!@#$%^&*()_+-=[]{}");

        String pool = alphabet.toString();

        if(request.excludeAmbiguous()) {
            pool = pool.replaceAll("[l1IO0oB8S5Z2]", "");
        }
        return pool;
    }

    private String generateSinglePassword(String alphabet, int length) {
        StringBuilder password = new StringBuilder(length);

        for(int i = 0; i < length; i++) {
            int randomIndex = RANDOM.nextInt(alphabet.length());
            password.append(alphabet.charAt(randomIndex));
        }

        return password.toString();
    }

    public PasswordResponse generate(PasswordRequest request) {
        String alphabet = buildAlphabet(request);

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
