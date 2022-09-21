package com.qudini.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MoreRsaTest {

    @Test
    void generateKeyPair() {
        var openSsh = MoreRsa.openSsh(4096);
        var keyPair = openSsh.generateKeyPair();
        assertThat(keyPair.getPublicKey()).startsWith("ssh-rsa ");
        assertThat(keyPair.getPublicKey()).endsWith(" qudini");
        assertThat(keyPair.getPrivateKey()).startsWith("-----BEGIN RSA PRIVATE KEY-----\n");
        assertThat(keyPair.getPrivateKey()).endsWith("\n-----END RSA PRIVATE KEY-----\n");
        openSsh.decodePublicKey(keyPair.getPublicKey());
    }

}
