package com.specture.core.exception;

import com.specture.core.constant.ErrorMessage;

public class MeasurementHistoryNotAccessibleException extends RuntimeException {
    public MeasurementHistoryNotAccessibleException(){
        super(ErrorMessage.MEASUREMENT_HISTORY_IS_NOT_AVAILABLE); // this message was simplified by product owner request
    }
}
