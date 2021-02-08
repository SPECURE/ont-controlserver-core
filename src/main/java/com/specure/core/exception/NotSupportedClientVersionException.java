package com.specure.core.exception;

import com.specure.core.constant.ErrorMessage;

public class NotSupportedClientVersionException extends RuntimeException {

    public NotSupportedClientVersionException(String name) {
        super(String.format(ErrorMessage.UNSUPPORTED_CLIENT_NAME, name));
    }
}
