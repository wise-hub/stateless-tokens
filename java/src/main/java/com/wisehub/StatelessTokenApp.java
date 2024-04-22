package com.wisehub;

public class StatelessTokenApp {

    public static void main(String[] args) {

        String secret = "jThHzfY9mFTH9RN78yPvWYcVRMXBkU3X";
        String nonce = "Mx6rcI7E2s75Be4N";
        String username = "mihaela";
        String userAgent = "1Mozzzzzill12313ettreta/5.0 (Windo';';;'';1'23';1!@#!%!!%!$><><>''';;';'ws NT 10.0; Win64; x64) changed1233";

        // CSRF
        String csrfToken = CsrfTokenUtil.generateCsrfToken(nonce, secret);
        System.out.println("Java CSRF Token: " + csrfToken);
        System.out.println("Valid: " + CsrfTokenUtil.verifyCsrfToken(csrfToken, secret));

        // Login
        String loginToken = LoginTokenUtil.generateLoginToken(username, userAgent, nonce, secret);
        System.out.println("Java Login Token: " + loginToken);
        System.out.println("Valid: " + LoginTokenUtil.verifyLoginToken(loginToken, userAgent, secret));
    }


}






