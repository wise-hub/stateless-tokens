package com.wisehub;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class CsrfTokenUtil {
    public static String generateCsrfToken(String nonce, String secret) {
        try {
            long time = System.currentTimeMillis();
            String dynamicSecret = secret + nonce;

            Mac hmac = Mac.getInstance("HmacSHA256");
            hmac.init(new SecretKeySpec(dynamicSecret.getBytes(), "HmacSHA256"));
            hmac.update(nonce.getBytes());
            String token = Base64.getUrlEncoder().withoutPadding().encodeToString(hmac.doFinal());

            return time + "." + nonce + "." + token;
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            System.err.println("Error generating CSRF token: " + e.getMessage());
            return null;
        }
    }

    public static boolean verifyCsrfToken(String token, String secret) {
        try {
            String[] tokenParts = token.split("\\.");
            if (tokenParts.length != 3) return false;

            long timestamp = Long.parseLong(tokenParts[0]);
            String nonce = tokenParts[1];
            String tokenOnly = tokenParts[2];
            long maxDuration = 5 * 60 * 1000; // 5 min
            long currentDate = System.currentTimeMillis();
            if (currentDate - timestamp > maxDuration) return false;

            String dynamicSecret = secret + nonce;
            Mac hmac = Mac.getInstance("HmacSHA256");
            hmac.init(new SecretKeySpec(dynamicSecret.getBytes(), "HmacSHA256"));
            hmac.update(nonce.getBytes());
            String expectedToken = Base64.getUrlEncoder().withoutPadding().encodeToString(hmac.doFinal());

            return tokenOnly.equals(expectedToken);
        } catch (NoSuchAlgorithmException | InvalidKeyException | NumberFormatException e) {
            System.err.println("Error validating CSRF token: " + e.getMessage());
            return false;
        }
    }
}
