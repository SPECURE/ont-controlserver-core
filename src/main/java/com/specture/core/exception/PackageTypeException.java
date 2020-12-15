package com.specture.core.exception;

import com.specture.core.constant.ErrorMessage;

public class PackageTypeException extends RuntimeException {

    public PackageTypeException(String packageTypeName) {
        super(String.format(ErrorMessage.BAD_PACKAGE_TYPE_NAME, packageTypeName));
    }
}
