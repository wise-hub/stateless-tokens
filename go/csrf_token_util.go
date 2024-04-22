package main

import (
    "crypto/hmac"
    "crypto/sha256"
    "encoding/base64"
    "fmt"
    "strconv"
    "strings"
    "time"
)

func generateCsrfToken(nonce, secret string) string {
    encoder := base64.URLEncoding.WithPadding(base64.NoPadding)
    currentTime := time.Now().UnixMilli()
    dynamicSecret := secret + nonce

    hmac := hmac.New(sha256.New, []byte(dynamicSecret))
    hmac.Write([]byte(nonce))
    token := encoder.EncodeToString(hmac.Sum(nil))

    return fmt.Sprintf("%d.%s.%s", currentTime, nonce, token)
}

func validateCsrfToken(token, secret string) bool {
    encoder := base64.URLEncoding.WithPadding(base64.NoPadding)
    parts := strings.Split(token, ".")
    if len(parts) != 3 {
        return false
    }

    timestamp, err := strconv.ParseInt(parts[0], 10, 64)
    if err != nil {
        fmt.Println("Error parsing timestamp:", err)
        return false
    }

    nonce := parts[1]
    tokenOnly := parts[2]
    maxDuration := int64(5 * 60 * 1000) // 5 min
    currentTime := time.Now().UnixMilli()
    if currentTime-timestamp > maxDuration {
        return false
    }

    dynamicSecret := secret + nonce
    hmac := hmac.New(sha256.New, []byte(dynamicSecret))
    hmac.Write([]byte(nonce))
    expectedToken := encoder.EncodeToString(hmac.Sum(nil))

    return tokenOnly == expectedToken
}