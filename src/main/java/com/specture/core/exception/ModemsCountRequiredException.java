package com.specture.core.exception;

import com.specture.core.constant.ErrorMessage;

public class ModemsCountRequiredException extends RuntimeException {

    public ModemsCountRequiredException() {
        super(ErrorMessage.MODEM_COUNT_REQUIRED);
    }

}
