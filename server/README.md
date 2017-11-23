# Server in mTLS

This is simple server to demonstrate mTLS authentication. Server SSL is configured in standard way with keystores provided in 
classpath.

## Creating keystores

Use _util/generate_selfsigned_keystores.sh_ to generate server and client keystores/truststores and PKCS12 for testing via browser.

## Testing

Make sure you imported PKCS12 cert into your browser/system keychain.
If everything set well and application is running, you should be able to open https://localhost:8443/user in your browser.

If you remove PKCS12 certificate from keychain calling https://localhost:8443/user should return ERR_BAD_SSL_CLIENT_AUTH_CERT.