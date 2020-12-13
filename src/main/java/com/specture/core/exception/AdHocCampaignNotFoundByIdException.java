package com.specture.core.exception;

import com.specture.core.constant.ErrorMessage;

public class AdHocCampaignNotFoundByIdException extends RuntimeException {

    public AdHocCampaignNotFoundByIdException(String id) {
        super(String.format(ErrorMessage.AD_HOC_CAMPAIGN_WAS_NOT_FOUND_BY_ID, id));
    }
}
