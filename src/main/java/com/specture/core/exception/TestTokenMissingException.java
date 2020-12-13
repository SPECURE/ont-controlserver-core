package com.specture.core.exception;

import com.specture.core.constant.ErrorMessage;

public class TestTokenMissingException extends RuntimeException {
    public TestTokenMissingException() {
        super(ErrorMessage.TEST_TOKEN_REQUIRED);
    }
}
