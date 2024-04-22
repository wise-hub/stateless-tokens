package com.wisehub;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

public class LoginTokenUtil {

    public static String generateLoginToken(String username, String userAgent, String nonce, String secret) {
        try {
            String encodedUsername = Base64.getUrlEncoder().withoutPadding().encodeToString(username.getBytes());
            String dynamicSecret = secret + encodedUsername + nonce;
            String trimmedUserAgent = userAgent.substring(0, Math.min(120, userAgent.length()));

            Mac hmac = Mac.getInstance("HmacSHA256");
            hmac.init(new SecretKeySpec(dynamicSecret.getBytes(), "HmacSHA256"));
            hmac.update(trimmedUserAgent.getBytes());
            String token = Base64.getUrlEncoder().withoutPadding().encodeToString(hmac.doFinal());

            return String.format("%s.%s.%s", encodedUsername, nonce, token);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            System.err.println("Error generating login token: " + e.getMessage());
            return null;
        }
    }

    public static boolean verifyLoginToken(String token, String userAgent, String secret) {
        try {
            String[] tokenParts = token.split("\\.");
            if (tokenParts.length != 3) return false;

            String encodedUsername = tokenParts[0];
            String username = new String(Base64.getUrlDecoder().decode(encodedUsername));
            String nonce = tokenParts[1];
            String regeneratedToken = generateLoginToken(username, userAgent, nonce, secret);

            return token.equals(regeneratedToken);
        } catch (Exception e) {
            System.err.println("Error verifying login token: " + e.getMessage());
            return false;
        }
    }
}
