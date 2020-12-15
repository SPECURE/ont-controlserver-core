package com.specture.core.exception.wellconfiguredport;

import static com.specture.core.constant.ErrorMessage.PROBE_PORT_IS_NULL;

public class ProbePortIsNullException extends RuntimeException {
    public ProbePortIsNullException() {
        super(PROBE_PORT_IS_NULL);
    }
}
