package com.specture.core.exception;

import com.specture.core.constant.ErrorMessage;

public class MeasurementFormatException extends RuntimeException {
    public MeasurementFormatException(String wrongFormatMeasurementDescription) {
        super(String.format(ErrorMessage.MEASUREMENT_RESULT_WRONG_FORMAT, wrongFormatMeasurementDescription));
    }
}
