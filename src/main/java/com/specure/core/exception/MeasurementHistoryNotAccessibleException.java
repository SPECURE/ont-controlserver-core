package com.specure.core.exception;

import com.specure.core.constant.ErrorMessage;

public class MeasurementHistoryNotAccessibleException extends RuntimeException {
    public MeasurementHistoryNotAccessibleException(){
        super(ErrorMessage.MEASUREMENT_HISTORY_IS_NOT_AVAILABLE); // this message was simplified by product owner request
    }
}
