package com.qudini.utils.rsa;

import lombok.Value;

@Value
public class OpenSshKeyPair {

    String publicKey;
    String privateKey;

}
