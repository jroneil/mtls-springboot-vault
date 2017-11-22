package com.example.mutualssl.keystore;

import com.google.common.io.Files;
import org.junit.*;
import org.junit.contrib.java.lang.system.EnvironmentVariables;

import java.io.File;
import java.security.KeyStore;

import static org.junit.Assert.*;

/**
 * Created by sding on 5/15/17.
 * This is supposed to be integration tests. Require a vault dependency setup
 */

//@Ignore
public class VaultKeyStoreFactoryTest {

    @Rule
    public final EnvironmentVariables environmentVariables = new EnvironmentVariables();

    @Before
    public void setup(){
        environmentVariables.set(LoadKeyStore.TRUST_STORE, "jks_store/server_trust");
        environmentVariables.set(LoadKeyStore.TRUST_STORE_PASSWORD, "secret/server_trust_pass");
        environmentVariables.set(LoadKeyStore.KEY_STORE, "jks_store/server");
        environmentVariables.set(LoadKeyStore.KEY_STORE_PASSWORD, "secret/server_pass");
        environmentVariables.set(LoadKeyStore.BACK_END, "vault");
        environmentVariables.set(VaultKeyStoreFactory.VAULT_TOKEN, "761b8203-f29c-da30-8863-0c4286076c51");
        environmentVariables.set(VaultKeyStoreFactory.VAULT_SERVER, "http://127.0.0.1:8200");
    }

    @Test
    public void createKeyStoreConfig() throws Exception {
        VaultKeyStoreFactory vaultKeyStoreFactory = new VaultKeyStoreFactory();
        KeyStoreConfig config = vaultKeyStoreFactory.createKeyStoreConfig();
        Assert.assertTrue(Files.asByteSource(new File(config.getKeyStore())).contentEquals(Files.asByteSource(new File("src/test/resources/server-keystore.jks"))));
        Assert.assertEquals("changeit", config.getKeyStorePass());

        Assert.assertTrue(Files.asByteSource(new File(config.getTrustStore())).contentEquals(Files.asByteSource(new File("src/test/resources/server-truststore.jks"))));
        Assert.assertEquals("changeit", config.getTrustStorePass());
    }

}