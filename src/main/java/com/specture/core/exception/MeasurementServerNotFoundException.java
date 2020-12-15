package com.specture.core.exception;

import com.specture.core.constant.ErrorMessage;

public class MeasurementServerNotFoundException extends RuntimeException {
    public MeasurementServerNotFoundException (Long id){
        super((String.format(ErrorMessage.MEASUREMENT_SERVER_NOT_FOUND, id)));
    }
}
