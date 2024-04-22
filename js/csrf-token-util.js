const crypto = require('crypto');

function generateCsrfToken(nonce, secret) {
    const time = Date.now();
    const dynamicSecret = `${secret}${nonce}`;

    const hmac = crypto.createHmac('sha256', dynamicSecret);
    hmac.update(nonce);
    const token = hmac.digest('base64url');

    return `${time}.${nonce}.${token}`;
}

function validateCsrfToken(token, secret) {
    const tokenParts = token.split('.');
    if (tokenParts.length !== 3) {
        return false;
    }
    const timestamp = tokenParts[0];
    const nonce = tokenParts[1];
    const tokenOnly = tokenParts[2];
    const maxDuration = 5 * 60 * 1000; // 5 min
    const currentDate = Date.now();
    if (currentDate - timestamp > maxDuration) {
        return false;
    }

    const dynamicSecret = `${secret}${nonce}`;
    const hmac = crypto.createHmac('sha256', dynamicSecret);
    hmac.update(nonce);
    const expectedToken = hmac.digest('base64url');

    return tokenOnly === expectedToken;
}

module.exports = { generateCsrfToken, validateCsrfToken };
