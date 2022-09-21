package com.qudini.utils.rsa;

import lombok.RequiredArgsConstructor;
import org.bouncycastle.openssl.jcajce.JcaMiscPEMGenerator;
import org.bouncycastle.util.io.pem.PemWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * <p>Utilities around OpenSSH, thread-safe.</p>
 */
@RequiredArgsConstructor
public final class OpenSsh {

    private static final int PUBLIC_KEY_VALUE_LENGTH = 4;
    private static final byte[] PUBLIC_KEY_PREFIX = new byte[]{0x00, 0x00, 0x00, 0x07, 0x73, 0x73, 0x68, 0x2d, 0x72, 0x73, 0x61};
    private static final Pattern PUBLIC_KEY_PATTERN = Pattern.compile("ssh-rsa\\s+([A-Za-z\\d/+]+=*)\\s+.*");
    private static final String DEFAULT_COMMENT = "qudini";

    private final int keySize;

    /**
     * <p>Generates an OpenSSH key pair.</p>
     */
    public OpenSshKeyPair generateKeyPair() {
        return generateKeyPair(DEFAULT_COMMENT);
    }

    /**
     * <p>Generates an OpenSSH key pair.</p>
     */
    public OpenSshKeyPair generateKeyPair(String publicKeyComment) {
        var keyPair = newKeyPairGenerator().generateKeyPair();
        var publicKey = encodePublicKey((RSAPublicKey) keyPair.getPublic(), publicKeyComment);
        var privateKey = encodePrivateKey((RSAPrivateKey) keyPair.getPrivate());
        return new OpenSshKeyPair(publicKey, privateKey);
    }

    /**
     * <p>Encodes the public key in OpenSSH public key format.</p>
     */
    public String encodePublicKey(RSAPublicKey publicKey) {
        return encodePublicKey(publicKey, DEFAULT_COMMENT);
    }

    /**
     * <p>Encodes the public key in OpenSSH public key format.</p>
     */
    public String encodePublicKey(RSAPublicKey publicKey, String comment) {
        try (
                var baos = new ByteArrayOutputStream();
                var dos = new DataOutputStream(baos)
        ) {
            dos.writeInt("ssh-rsa".getBytes().length);
            dos.write("ssh-rsa".getBytes());
            dos.writeInt(publicKey.getPublicExponent().toByteArray().length);
            dos.write(publicKey.getPublicExponent().toByteArray());
            dos.writeInt(publicKey.getModulus().toByteArray().length);
            dos.write(publicKey.getModulus().toByteArray());
            return "ssh-rsa " + new String(Base64.getEncoder().encode(baos.toByteArray())) + " " + comment;
        } catch (IOException e) {
            throw new OpenSshException("An error occurred while encoding a public key", e);
        }
    }

    /**
     * <p>Encodes the given key in PEM base64-encoded format.</p>
     */
    public String encodePrivateKey(RSAPrivateKey privateKey) {
        try (
                var sw = new StringWriter();
                var pw = new PemWriter(sw)
        ) {
            pw.writeObject(new JcaMiscPEMGenerator(privateKey));
            pw.flush();
            return sw.toString();
        } catch (IOException e) {
            throw new OpenSshException("An error occurred while encoding a private key", e);
        }
    }

    /**
     * <p>Expects the given key to be in OpenSSH public key format.</p>
     */
    public RSAPublicKey decodePublicKey(String key) {
        var matcher = PUBLIC_KEY_PATTERN.matcher(key.trim());
        if (!matcher.matches()) {
            throw new OpenSshException("Unable to decode public key: format is invalid for SSH RSA");
        }
        var base64 = matcher.group(1);
        try (var bais = new ByteArrayInputStream(Base64.getDecoder().decode(base64))) {
            var prefix = new byte[PUBLIC_KEY_PREFIX.length];
            if (PUBLIC_KEY_PREFIX.length != bais.read(prefix) || !Objects.deepEquals(PUBLIC_KEY_PREFIX, prefix)) {
                throw new OpenSshException("Unable to decode public key: initial [ssh-rsa] key prefix is missing");
            }
            var exponent = getValue(bais);
            var modulus = getValue(bais);
            return (RSAPublicKey) newKeyFactory().generatePublic(new RSAPublicKeySpec(modulus, exponent));
        } catch (IOException | InvalidKeySpecException e) {
            throw new OpenSshException("Unable to decode public key: failed to read SSH RSA certificate", e);
        }
    }

    private BigInteger getValue(InputStream is) throws IOException {
        var lenBuff = new byte[PUBLIC_KEY_VALUE_LENGTH];
        if (PUBLIC_KEY_VALUE_LENGTH != is.read(lenBuff)) {
            throw new OpenSshException("Unable to decode public key: unable to read value length");
        }
        var len = ByteBuffer.wrap(lenBuff).getInt();
        var valueArray = new byte[len];
        if (len != is.read(valueArray)) {
            throw new OpenSshException("Unable to decode public key: unable to read value");
        }
        return new BigInteger(valueArray);
    }

    private KeyPairGenerator newKeyPairGenerator() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(keySize);
            return generator;
        } catch (NoSuchAlgorithmException e) {
            throw new OpenSshException("An error occurred while creating a key pair generator", e);
        }
    }

    private KeyFactory newKeyFactory() {
        try {
            return KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new OpenSshException("An error occurred while creating a key factory", e);
        }
    }

}
