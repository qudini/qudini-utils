package com.qudini.utils;

import com.qudini.utils.rsa.OpenSsh;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

/**
 * <p>Utilities around RSA.</p>
 */
@NoArgsConstructor(access = PRIVATE)
public final class MoreRsa {

    /**
     * <p>Returns a new thread-safe OpenSSH utility.</p>
     */
    public static OpenSsh openSsh(int keySize) {
        return new OpenSsh(keySize);
    }

}
