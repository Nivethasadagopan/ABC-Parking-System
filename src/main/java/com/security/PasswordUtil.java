package com.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtil {

    private static final int SALT_LENGTH = 16;
    private static final int HASH_LENGTH = 64;
    private static final int ITERATIONS = 10000;

    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(salt);
        byte[] hash = digest.digest(password.getBytes());

        for (int i = 0; i < ITERATIONS; i++) {
            hash = digest.digest(hash);
        }

        byte[] hashWithSalt = new byte[SALT_LENGTH + HASH_LENGTH];
        System.arraycopy(salt, 0, hashWithSalt, 0, SALT_LENGTH);
        System.arraycopy(hash, 0, hashWithSalt, SALT_LENGTH, HASH_LENGTH);

        return Base64.getEncoder().encodeToString(hashWithSalt);
    }

    public static boolean verifyPassword(String password, String storedHash) throws NoSuchAlgorithmException {
        byte[] hashWithSalt = Base64.getDecoder().decode(storedHash);
        byte[] salt = new byte[SALT_LENGTH];
        System.arraycopy(hashWithSalt, 0, salt, 0, SALT_LENGTH);

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(salt);
        byte[] hash = digest.digest(password.getBytes());

        for (int i = 0; i < ITERATIONS; i++) {
            hash = digest.digest(hash);
        }

        for (int i = 0; i < HASH_LENGTH; i++) {
            if (hash[i] != hashWithSalt[SALT_LENGTH + i]) {
                return false;
            }
        }

        return true;
    }
}
