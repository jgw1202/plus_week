package com.example.demo.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordEncoderTest {

    @Test
    void encode() {
        // given
        String rawPassword = "mySecret123";

        // when
        String encodedPassword = PasswordEncoder.encode(rawPassword);

        // then
        assertNotNull(encodedPassword, "Encoded password should not be null");
        assertNotEquals(rawPassword, encodedPassword, "Encoded password should not be the same as the raw password");
    }

    @Test
    void matches() {
        // given
        String rawPassword = "mySecret123";
        String encodedPassword = PasswordEncoder.encode(rawPassword);

        // when
        boolean matches = PasswordEncoder.matches(rawPassword, encodedPassword);
        boolean wrongMatches = PasswordEncoder.matches("wrongPassword", encodedPassword);

        // then
        assertTrue(matches, "Encoded password should match the raw password");
        assertFalse(wrongMatches, "Encoded password should not match the wrong password");
    }
}
