package com.specture.core.exception;

import com.specture.core.constant.ErrorMessage;

import java.util.Set;

public class ProbesNotFoundException extends RuntimeException {

    public ProbesNotFoundException(Set<String> id) {
        super(String.format(ErrorMessage.PROBES_NOT_FOUND, id));
    }

}
