package org.bitbucket.poad1010.example.springboot.batch.exception;

import java.security.GeneralSecurityException;

/**
 * Created by ken-yo on 2016/08/07.
 */
public class UncheckedGeneralSecurityException extends RuntimeException {
    private GeneralSecurityException cause;

    public UncheckedGeneralSecurityException(GeneralSecurityException cause) {
        super(cause);
        this.cause = cause;
    }

    @Override
    public GeneralSecurityException getCause() {
        return cause;
    }
}
