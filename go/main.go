package main

import "fmt"

func main() {

    secret := "jThHzfY9mFTH9RN78yPvWYcVRMXBkU3X"
    nonce := "Mx6rcI7E2s75Be4N"
	username := "mihaela"
	userAgent := "1Mozzzzzill12313ettreta/5.0 (Windo';';;'';1'23';1!@#!%!!%!$><><>''';;';'ws NT 10.0; Win64; x64) changed1233"


    // CSRF
    csrfToken := generateCsrfToken(nonce, secret)
    fmt.Println("Go CSRF Token:", csrfToken)
    fmt.Println("Valid:", validateCsrfToken(csrfToken, secret))

    // Login

	token := generateLoginToken(username, userAgent, nonce, secret)
	fmt.Println("Go Login Token:", token)
	fmt.Println("Valid:", verifyLoginToken(token, userAgent, secret))
}
