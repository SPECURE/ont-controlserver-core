package com.specture.core.exception;

import com.specture.core.constant.ErrorMessage;

public class PackageNotFoundException extends RuntimeException {

    public PackageNotFoundException(Long id) {
        super(String.format(ErrorMessage.PACKAGE_NOT_FOUND, id));
    }
}
