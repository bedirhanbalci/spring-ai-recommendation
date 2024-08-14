package com.patika.bloghubservice.util;

import com.patika.bloghubservice.exception.BlogHubException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashPassword {
    private static final Logger log = LoggerFactory.getLogger(HashPassword.class);

    // nesnesini hiç bir zaman oluşturmamayı düşündüğüm bir class olduğu için ileride
    // oluşabilecek bir karışıklığı önlemek adına private constructor tanımladım.
    private HashPassword() {}

    public static String hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            byte[] messageDigest = md.digest(password.getBytes());

            BigInteger no = new BigInteger(1, messageDigest);

            StringBuilder hashtext = new StringBuilder(no.toString(16));

            while (hashtext.length() < 32) {
                hashtext.insert(0, "0");
            }

            return hashtext.toString();
        }
        catch (NoSuchAlgorithmException e) {
            log.error("hashing error: {}", e.getMessage());
            throw new BlogHubException(e.getMessage());
        }
    }
}