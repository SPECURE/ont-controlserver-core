package com.specture.core.exception;

import com.specture.core.constant.ErrorMessage;

public class ProbeNotFoundException extends RuntimeException {

    public ProbeNotFoundException(String id) {
        super(String.format(ErrorMessage.PROBE_NOT_FOUND, id));
    }

}
