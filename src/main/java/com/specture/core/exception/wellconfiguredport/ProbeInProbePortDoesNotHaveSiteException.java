package com.specture.core.exception.wellconfiguredport;

import static com.specture.core.constant.ErrorMessage.PROBE_IN_PROBE_PORT_DOES_NOT_HAVE_SITE;

public class ProbeInProbePortDoesNotHaveSiteException extends RuntimeException {
    public ProbeInProbePortDoesNotHaveSiteException(String probeId, String probePortName) {
        super( String.format(PROBE_IN_PROBE_PORT_DOES_NOT_HAVE_SITE, probeId, probePortName));
    }
}
