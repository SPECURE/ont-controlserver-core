package com.specture.core.exception;

import com.specture.core.constant.ErrorMessage;

public class ProbePortNotFoundInProbeException extends RuntimeException {

    public ProbePortNotFoundInProbeException(String port, String probe) {
        super(String.format(ErrorMessage.PROBE_PORT_NOT_FOUND_IN_PROBE, port, probe));
    }

}
