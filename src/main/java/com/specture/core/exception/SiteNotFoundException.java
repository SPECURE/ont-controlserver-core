package com.specture.core.exception;

import com.specture.core.constant.ErrorMessage;

public class SiteNotFoundException extends RuntimeException {

    public SiteNotFoundException(Long id) {
        super(String.format(ErrorMessage.SITE_NOT_FOUND, id));
    }

}
