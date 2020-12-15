package com.specture.core.exception;

import com.specture.core.constant.ErrorMessage;

public class ChangePortsAmountWithPackagesException extends RuntimeException {

    public ChangePortsAmountWithPackagesException() {
        super(ErrorMessage.ATTEMPT_TO_CHANGE_PORTS_AMOUNT_WITH_PACKAGES_SET_UP);
    }

}
