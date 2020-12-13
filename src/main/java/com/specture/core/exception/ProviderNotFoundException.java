package com.specture.core.exception;

import com.specture.core.constant.ErrorMessage;

public class ProviderNotFoundException extends RuntimeException {

    public ProviderNotFoundException(Long id) {
        super(String.format(ErrorMessage.PROVIDER_NOT_FOUND_BY_ID, id));
    }

}
