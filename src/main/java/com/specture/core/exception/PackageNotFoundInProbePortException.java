package com.specture.core.exception;

import com.specture.core.constant.ErrorMessage;

public class PackageNotFoundInProbePortException extends RuntimeException {

    public PackageNotFoundInProbePortException(Long id) {
        super(String.format(ErrorMessage.PACKAGE_NOT_FOUND_IN_PROBE_PORT, id));
    }
}
