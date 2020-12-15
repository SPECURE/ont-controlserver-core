package com.specture.core.exception;

import com.specture.core.model.Provider;
import com.specture.core.constant.ErrorMessage;

public class MeasurementServerNotFoundByProviderOnOffNetException extends RuntimeException {

    public MeasurementServerNotFoundByProviderOnOffNetException(Provider provider, Boolean isOnNet) {
        super(String.format(ErrorMessage.MEASUREMENT_SERVER_ON_OFF_NET_NOT_FOUND, isOnNet, provider.getName()));
    }
}
