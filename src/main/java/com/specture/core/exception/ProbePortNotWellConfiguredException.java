package com.specture.core.exception;

import com.specture.core.constant.ErrorMessage;

public class ProbePortNotWellConfiguredException extends RuntimeException {

    public ProbePortNotWellConfiguredException(String port, String probe) {
        super(String.format(ErrorMessage.PROBE_PORT_NOT_CONFIGURED, probe, port));
    }

}
