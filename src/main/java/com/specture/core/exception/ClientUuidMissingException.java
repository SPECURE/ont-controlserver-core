package com.specture.core.exception;

import com.specture.core.constant.ErrorMessage;

public class ClientUuidMissingException extends RuntimeException {
    public ClientUuidMissingException() {
        super(ErrorMessage.CLIENT_UUID_REQUIRED);
    }
}
