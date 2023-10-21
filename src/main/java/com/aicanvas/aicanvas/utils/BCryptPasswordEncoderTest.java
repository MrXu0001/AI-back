package com.aicanvas.aicanvas.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordEncoderTest {
    public static void main(String[] args) {
        String password = "123456";

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String passHash = encoder.encode(password);
        System.out.println(passHash);

        final boolean matches = encoder.matches(password, passHash);
        System.out.println(matches);
    }

}
