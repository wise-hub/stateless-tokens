const crypto = require('crypto');

function generateLoginToken(username, userAgent, nonce, secret) {
    const encodedUsername = Buffer.from(username).toString('base64url');
    const dynamicSecret = secret + encodedUsername + nonce;
    const trimmedUserAgent = userAgent.substring(0, Math.min(120, userAgent.length));

    const hmac = crypto.createHmac('sha256', dynamicSecret);
    hmac.update(trimmedUserAgent);
    const token = hmac.digest('base64url');

    return `${encodedUsername}.${nonce}.${token}`;
}

function verifyLoginToken(token, userAgent, secret) {
    const tokenParts = token.split('.');
    if (tokenParts.length !== 3) {
        return false;
    }
    const encodedUsername = tokenParts[0];
    const username = Buffer.from(encodedUsername, 'base64url').toString();
    const nonce = tokenParts[1]
    const tokenOnly = tokenParts[2];
    const regeneratedToken = generateLoginToken(username, userAgent, nonce, secret);

    return token === regeneratedToken;
}

module.exports = { generateLoginToken, verifyLoginToken };
