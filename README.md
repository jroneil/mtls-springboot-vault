# mTLS with Vault

This excercise shows how to use Hashicorp Vault to store client secrets. This example is not production ready!

## Setup
We have one simple server protected with mTLS and one client that is configured to be able to talk with server over mTLS.
Before starting applications, do the following:
1. execute _util/generate_selfsigned_keystores.sh_ to generate client and server keystores and certificates
2. start and un-seal Vault
3. import previously generated keystores as Vault secret values by executing _util/import_values_to_vault.sh_

## Vault configuration

Vault and client application are deployed and configured in a way to meet client requirements. For example,
Vault has to have all secrets client need for mTLS and has to be un-sealed. How to run Vault is out of scope, you can
find some information [here][1]. To store keystore files on Vault we have to encode their content with Base64.

### Deploy Vault on Cloud Foundry

Default storage space for Vault is in-memory. If you want to deploy it on CF this is not setup you want, because 
every restart will cause lost of data. To gain persistency, you may deploy Vault with some popular data store 
from CF marketplace as described [here][2].

## LICENCE
You are free to use and change this software without any charge or legal claims. The author takes no responsibility
if you use this software as is in improper way or in production environment.

[1]: http://cloud.spring.io/spring-cloud-vault/spring-cloud-vault-config.html
[2]: https://github.com/making/cf-vault