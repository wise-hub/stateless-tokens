const { generateLoginToken, verifyLoginToken } = require('./login-token-util');
const { generateCsrfToken, validateCsrfToken } = require('./csrf-token-util');

const secret = "jThHzfY9mFTH9RN78yPvWYcVRMXBkU3X"
const nonce = "Mx6rcI7E2s75Be4N";
const username = "mihaela";
const userAgent = "1Mozzzzzill12313ettreta/5.0 (Windo';';;'';1'23';1!@#!%!!%!$><><>''';;';'ws NT 10.0; Win64; x64) changed1233";

// CSRF
const csrfToken = generateCsrfToken(nonce, secret);
console.log('JS CSRF Token:', csrfToken);
console.log('Valid:', validateCsrfToken(csrfToken, secret));

// Login
const loginToken = generateLoginToken(username, userAgent, nonce, secret);
console.log('JS Login Token:', loginToken);
console.log('Valid:', verifyLoginToken(loginToken, userAgent, secret));

