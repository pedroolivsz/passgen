package com.io.github.pedroolivsz.passgen.service;

import com.io.github.pedroolivsz.passgen.DTOs.PasswordRequest;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

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
}
