package com.specture.core.exception;

import com.specture.core.constant.ErrorMessage;

public class ProviderAlreadyExistsException extends RuntimeException {

    public ProviderAlreadyExistsException(Long id) {
        super(String.format(ErrorMessage.PROVIDER_WITH_ID_ALREADY_EXISTS, id));
    }

}
