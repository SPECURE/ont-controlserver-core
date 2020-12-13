package com.specture.core.exception;

import com.specture.core.constant.ErrorMessage;

public class ProbePortNotFoundByNameAndProbeIdException extends RuntimeException {

    public ProbePortNotFoundByNameAndProbeIdException(String name, String probeId) {
        super(String.format(ErrorMessage.PROBE_PORT_NOT_FOUND_BY_NAME_AND_PROBE_ID, name, probeId));
    }

}
