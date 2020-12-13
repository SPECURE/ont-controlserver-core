package com.specture.core.exception;

import com.specture.core.constant.ErrorMessage;

public class SiteAdvertisedIsDuplicatedException extends RuntimeException {

    public SiteAdvertisedIsDuplicatedException(String advertisedId) {
        super(String.format(ErrorMessage.SITE_EXISTS, advertisedId));
    }
}
