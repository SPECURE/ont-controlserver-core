package com.specure.core.exception;

import com.specure.core.constant.ErrorMessage;

public class QoSMeasurementServerNotFoundByUuidException extends RuntimeException {

    public QoSMeasurementServerNotFoundByUuidException(String uuid) {
        super(String.format(ErrorMessage.QOS_MEASUREMENT_SERVER_FOR_UUID_NOT_FOUND, uuid));
    }
}
