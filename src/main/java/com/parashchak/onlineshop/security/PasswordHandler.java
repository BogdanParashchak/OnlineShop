package com.parashchak.onlineshop.security;

import org.assertj.core.util.VisibleForTesting;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.util.Base64;

class PasswordHandler {

    @VisibleForTesting
    byte[] getKey(String password, String salt) {
        int iterations = 10000;
        int keyLength = 256;
        char[] passwordChars = password.toCharArray();
        byte[] saltBytes = salt.getBytes();
        PBEKeySpec spec = new PBEKeySpec(passwordChars, saltBytes, iterations, keyLength);

        try {
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return secretKeyFactory.generateSecret(spec).getEncoded();
        } catch (Exception e) {
            throw new RuntimeException("Error while key generation", e);
        }
    }

    @VisibleForTesting
    String generateEncryptedPassword(String password, String salt) {
        byte[] key = getKey(password, salt);
        return Base64.getEncoder().encodeToString(key);
    }

    boolean verifyPassword(String providedPassword, String salt, String encryptedPassword) {
        String newSecurePassword = generateEncryptedPassword(providedPassword, salt);
        return newSecurePassword.equalsIgnoreCase(encryptedPassword);
    }
}