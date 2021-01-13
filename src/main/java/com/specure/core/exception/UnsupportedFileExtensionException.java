package com.specure.core.exception;

import com.specure.core.constant.ErrorMessage;

public class UnsupportedFileExtensionException extends RuntimeException {

    public UnsupportedFileExtensionException(String fileExtension) {
        super(String.format(ErrorMessage.UNSUPPORTED_FILE_EXTENSION, fileExtension));
    }
}
