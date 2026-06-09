package com.io.github.pedroolivsz.passgen.service;

import com.io.github.pedroolivsz.passgen.DTOs.PasswordRequest;
import com.io.github.pedroolivsz.passgen.DTOs.PasswordResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PasswordServiceTest {
    @InjectMocks
    private PasswordService passwordService;

    @Test
    void generate_whenAlphabetIsEmpty_throwsIllegalArgumentException() {
        PasswordRequest request = new PasswordRequest(
                null, false, false, false, false, null, false
        );

        assertThrows(IllegalArgumentException.class,
                () -> passwordService.generate(request));
    }

    @Test
    void generate_whenQuantityIsNull_generatesOneSinglePassword() {
        PasswordRequest request = new PasswordRequest(
                8, true, true, true, true, null, false
        );

        PasswordResponse response = passwordService.generate(request);

        assertEquals(1, response.passwords().size());
    }

    @Test
    void generate_whenLengthIsNull_usesDefaultLengthOf16() {
        PasswordRequest request = new PasswordRequest(
                null, true, true, true, true, 1, false
        );

        PasswordResponse response = passwordService.generate(request);

        assertEquals(16, response.passwordLength());
        assertEquals(16, response.passwords().getFirst().length());
    }

    @Test
    void generate_withExplicitValues_generatesCorrectQuantityAndLength() {
        PasswordRequest request = new PasswordRequest(
                11, true, true, false, false, 9, false
        );

        PasswordResponse response = passwordService.generate(request);

        assertEquals(9, response.passwords().size());
        assertEquals(11, response.passwordLength());
        response.passwords().forEach(p -> assertEquals(11, p.length()));
    }

    @Test
    void generate_passwordsAreNotNullOrBlank() {
        PasswordRequest request = new PasswordRequest(
                11, true, true, true, false, 9, false
        );

        PasswordResponse response = passwordService.generate(request);

        response.passwords().forEach(p -> {
            assertNotNull(p);
            assertFalse(p.isBlank());
        });
    }

    @Test
    void generate_withOnlyUppercase_passwordConstrainsOnlyUppercaseLetters() {
        PasswordRequest request = new PasswordRequest(
                22, true, false, false, false, 1, false
        );

        String password = passwordService.generate(request).passwords().getFirst();

        assertTrue(password.chars().allMatch(Character::isUpperCase));
    }

    @Test
    void generate_withOnlyLowercase_passwordConstrainsOnlyLowercaseLetters() {
        PasswordRequest request = new PasswordRequest(
                22, false, true, false, false, 1, false
        );

        String password = passwordService.generate(request).passwords().getFirst();

        assertTrue(password.chars().allMatch(Character::isLowerCase));
    }

    @Test
    void generate_withOnlyNumber_passwordConstrainsOnlyNumberLetters() {
        PasswordRequest request = new PasswordRequest(
                22, false, false, true, false, 1, false
        );

        String password = passwordService.generate(request).passwords().getFirst();

        assertTrue(password.chars().allMatch(Character::isDigit));
    }

    @Test
    void generate_withOnlySymbols_passwordConstrainsOnlySymbolsLetters() {
        PasswordRequest request = new PasswordRequest(
                22, false, false, false, true, 1, false
        );

        String password = passwordService.generate(request).passwords().getFirst();

        assertTrue(password.chars().noneMatch(Character::isLetterOrDigit));
    }
}
