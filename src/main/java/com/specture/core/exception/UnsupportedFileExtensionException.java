package com.specture.core.exception;

import com.specture.core.constant.ErrorMessage;

public class UnsupportedFileExtensionException extends RuntimeException {

    public UnsupportedFileExtensionException(String fileExtension) {
        super(String.format(ErrorMessage.UNSUPPORTED_FILE_EXTENSION, fileExtension));
    }
}
