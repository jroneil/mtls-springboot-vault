
# the same key password we used when generating keystores
PASSWORD=changeit
#For demo purpose.... Assume Vault already authenticated

vault write secret/mtls-server server_trust=$(base64 server-truststore.jks) server=$(base64 server-keystore.jks) server_trust_pass=${PASSWORD} server_pass=${PASSWORD}
#vault write secret/mtls-client server=$(base64 server-keystore.jks)
#vault write secret/mtls-client server_trust_pass=${PASSWORD}
#vault write secret/mtls-client server_pass=${PASSWORD}
vault write secret/mtls-client client_trust=$(base64 client-truststore.jks) client=$(base64 client-keystore.jks) client_trust_pass=${PASSWORD} client_pass=${PASSWORD}
#vault write secret/mtls-client client=$(base64 client-keystore.jks)
#vault write secret/mtls-client client_trust_pass=${PASSWORD}
#vault write secret/mtls-client client_pass=${PASSWORD}
