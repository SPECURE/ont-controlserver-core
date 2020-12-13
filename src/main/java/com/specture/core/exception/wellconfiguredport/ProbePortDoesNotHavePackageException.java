package com.specture.core.exception.wellconfiguredport;

import static com.specture.core.constant.ErrorMessage.PROBE_PORT_DOES_NOT_HAVE_PACKAGE;

public class ProbePortDoesNotHavePackageException extends RuntimeException {
    public ProbePortDoesNotHavePackageException(String probePortName) {
        super( String.format(PROBE_PORT_DOES_NOT_HAVE_PACKAGE, probePortName));
    }
}
