package com.specture.core.exception;

import com.specture.core.constant.ErrorMessage;

public class ProviderNotFoundInPackageDataException extends RuntimeException {
    public ProviderNotFoundInPackageDataException(Long id) {
        super(String.format(ErrorMessage.PROVIDER_IN_PACKAGE_REQUIRED, id));
    }
}
