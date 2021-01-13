package com.specure.core.exception;

import com.specure.core.constant.ErrorMessage;

public class MeasurementServerNotFoundException extends RuntimeException {
    public MeasurementServerNotFoundException (Long id){
        super((String.format(ErrorMessage.MEASUREMENT_SERVER_NOT_FOUND, id)));
    }
}
