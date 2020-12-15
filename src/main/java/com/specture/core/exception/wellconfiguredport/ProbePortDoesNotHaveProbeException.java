package com.specture.core.exception.wellconfiguredport;

import static com.specture.core.constant.ErrorMessage.PROBE_PORT_DOES_NOT_HAVE_PROBE;

public class ProbePortDoesNotHaveProbeException extends RuntimeException {
    public ProbePortDoesNotHaveProbeException(String probePortName) {
        super( String.format(PROBE_PORT_DOES_NOT_HAVE_PROBE, probePortName));
    }
}
