package com.specture.core.exception;

import com.specture.core.constant.ErrorMessage;

public class AttemptToEditPackageAssignedToProbePortException extends RuntimeException {

    public AttemptToEditPackageAssignedToProbePortException(Long id) {
        super(String.format(ErrorMessage.PACKAGE_ASSIGNED_TO_PROBE_NOT_EDITABLE, id));
    }
}
