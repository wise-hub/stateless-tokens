package main

import (
	"crypto/hmac"
	"crypto/sha256"
	"encoding/base64"
	"fmt"
	"strings"
)

func min(a, b int) int {
	if a < b {
		return a
	}
	return b
}

func generateLoginToken(username, userAgent, nonce, secret string) string {
    encoder := base64.URLEncoding.WithPadding(base64.NoPadding)
    encodedUsername := encoder.EncodeToString([]byte(username))
    dynamicSecret := secret + encodedUsername + nonce
    trimmedUserAgent := userAgent[:min(120, len(userAgent))]

    hmac := hmac.New(sha256.New, []byte(dynamicSecret))
    hmac.Write([]byte(trimmedUserAgent))
    token := encoder.EncodeToString(hmac.Sum(nil))

    return fmt.Sprintf("%s.%s.%s", encodedUsername, nonce, token)
}

func verifyLoginToken(token, userAgent, secret string) bool {
    encoder := base64.URLEncoding.WithPadding(base64.NoPadding)
    parts := strings.Split(token, ".")
    if len(parts) != 3 {
        return false
    }

    encodedUsername := parts[0]
    usernameBytes, err := encoder.DecodeString(encodedUsername)
    if err != nil {
        fmt.Println("Error decoding username:", err)
        return false
    }
    username := string(usernameBytes)

    nonce := parts[1]
    regeneratedToken := generateLoginToken(username, userAgent, nonce, secret)

    return token == regeneratedToken
}