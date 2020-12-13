package com.specture.core.exception;

import com.specture.core.constant.ErrorMessage;

public class WrongNameOfUserExperienceParameterException extends RuntimeException {

    public WrongNameOfUserExperienceParameterException(String name) {
        super(String.format(ErrorMessage.WRONG_NAME_OF_USER_EXPERIENCE_PARAMETER, name));
    }

}
