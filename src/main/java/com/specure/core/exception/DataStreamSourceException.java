package com.specure.core.exception;

import com.specure.core.constant.ErrorMessage;

public class DataStreamSourceException extends RuntimeException {

    public DataStreamSourceException(String badDataStreamLabel) {
        super(String.format(ErrorMessage.DATA_STREAM_SOURCE, badDataStreamLabel));
    }
}
