package com.specture.core.exception;

import com.specture.core.constant.ErrorMessage;

public class QoSMeasurementServerNotFoundByUuidException extends RuntimeException {

    public QoSMeasurementServerNotFoundByUuidException(String uuid) {
        super(String.format(ErrorMessage.QOS_MEASUREMENT_SERVER_FOR_UUID_NOT_FOUND, uuid));
    }
}
