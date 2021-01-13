package com.specure.core.exception;

import com.specure.core.constant.ErrorMessage;

public class ProviderNotFoundByIdException extends RuntimeException {
    public ProviderNotFoundByIdException(Long id) {
        super(String.format(ErrorMessage.PROVIDER_NOT_FOUND_BY_ID, id));
    }
}
