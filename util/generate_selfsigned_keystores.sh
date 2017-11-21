PASSWORD=changeit

# CN = Common Name
# OU = Organization Unit
# O  = Organization Name
# L  = Locality Name
# ST = State Name
# C  = Country (2-letter Country Code)
# E  = Email
DNAME_CA='CN=RDrcelic CA,OU=RD,O=rdrcelic,L=mycity,ST=mystate,C=my'
# For server certificates, the Common Name (CN) must be the hostname
DNAME_HOST='CN=localhost,OU=RD,O=rdrcelic,L=mycity,ST=mystate,C=my'
DNAME_CLIENT='CN=myclient,OU=RD,O=rdrcelic,L=mycity,ST=mystate,C=my'

# Generate Server Keystore
keytool -genkeypair -alias secure-server -keyalg RSA -dname ${DNAME_HOST} -keypass ${PASSWORD} -keystore server-keystore.jks -storepass ${PASSWORD}
# Generate Client Keystore
keytool -genkeypair -alias secure-client -keyalg RSA -dname ${DNAME_CLIENT} -keypass ${PASSWORD} -keystore client-keystore.jks -storepass ${PASSWORD}

# Import the supported client's public certificates intro the server truststore
# Export the client public certificate
keytool -exportcert -alias secure-client -file client-public.cer -keystore client-keystore.jks -storepass ${PASSWORD}
# Import the client public certificate in the server truststore
keytool -importcert -keystore server-truststore.jks -alias clientcert -file client-public.cer -storepass ${PASSWORD}

# Import the server's public certificate into the client truststores
# Export the server public certificate
keytool -exportcert -alias secure-server -file server-public.cer -keystore server-keystore.jks -storepass ${PASSWORD}
# Import it in the client truststore
keytool -importcert -keystore client-truststore.jks -alias servercert -file server-public.cer -storepass ${PASSWORD}

# Prepare the certificate for access from the browser
#keytool -importkeystore -srckeystore client-keystore.jks -destkeystore my-secure-client.p12 -deststoretype PKCS12
# srcalias should be client common name (CN)
keytool -importkeystore -srckeystore client-keystore.jks -destkeystore my-secure-client.p12 -deststoretype PKCS12 -srcalias secure-client -srcstorepass ${PASSWORD} -deststorepass ${PASSWORD} 


