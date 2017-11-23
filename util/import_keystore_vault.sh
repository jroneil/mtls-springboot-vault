
# the same key password we used when generating keystores
PASSWORD=changeit
#For demo purpose.... Assume Vault already authenticated

vault mount -path jks_store generic
vault write jks_store/server_trust value=$(base64 server-truststore.jks)
vault write jks_store/server value=$(base64 server-keystore.jks)
vault write secret/server_trust_pass value=${PASSWORD}
vault write secret/server_pass value=${PASSWORD}
vault write jks_store/client_trust value=$(base64 client-truststore.jks)
vault write jks_store/client value=$(base64 client-keystore.jks)
vault write secret/client_trust_pass value=${PASSWORD}
vault write secret/client_pass value=${PASSWORD}
