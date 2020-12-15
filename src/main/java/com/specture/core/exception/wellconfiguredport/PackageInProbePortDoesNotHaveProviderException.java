package com.specture.core.exception.wellconfiguredport;

import static com.specture.core.constant.ErrorMessage.PACKAGE_IN_PROBE_PORT_DOES_NOT_HAVE_PROVIDER;

public class PackageInProbePortDoesNotHaveProviderException extends RuntimeException {
    public PackageInProbePortDoesNotHaveProviderException(String packageName, String probePortName) {
        super( String.format(PACKAGE_IN_PROBE_PORT_DOES_NOT_HAVE_PROVIDER, packageName, probePortName));
    }
}
