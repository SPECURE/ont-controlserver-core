package com.specture.core.exception;

import com.specture.core.constant.ErrorMessage;

public class OperatorRequiredException extends RuntimeException {

    public OperatorRequiredException() {
        super(ErrorMessage.OPERATOR_REQUIRED);
    }

}
