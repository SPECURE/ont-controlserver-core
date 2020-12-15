package com.specture.core.exception;

import com.specture.core.constant.ErrorMessage;

public class ProbeAlreadyMappedException extends RuntimeException {

    public ProbeAlreadyMappedException(String id) {
        super(String.format(ErrorMessage.PROBE_ALREADY_MAPPED, id));
    }

}
