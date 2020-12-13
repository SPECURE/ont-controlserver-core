package com.specture.core.exception;

import com.specture.core.constant.ErrorMessage;

public class ProbeIdIsDuplicatedException extends RuntimeException {
    public ProbeIdIsDuplicatedException(String message) {
        super(String.format(ErrorMessage.PROBE_EXISTS, message));
    }
}
