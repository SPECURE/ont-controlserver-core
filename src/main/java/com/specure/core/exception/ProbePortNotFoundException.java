package com.specure.core.exception;

import com.specure.core.constant.ErrorMessage;

public class ProbePortNotFoundException extends RuntimeException {

    public ProbePortNotFoundException(String port, String uuid) {
        super(String.format(ErrorMessage.PROBE_PORT_NOT_FOUND, port, uuid));
    }

    public ProbePortNotFoundException(Long port) {
        super(String.format(ErrorMessage.PROBE_PORT_NOT_FOUND_BY_ID, port));
    }

}
