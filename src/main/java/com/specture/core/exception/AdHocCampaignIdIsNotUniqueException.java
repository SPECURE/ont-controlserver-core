package com.specture.core.exception;

import com.specture.core.constant.ErrorMessage;

public class AdHocCampaignIdIsNotUniqueException extends RuntimeException {

    public AdHocCampaignIdIsNotUniqueException() {
        super(ErrorMessage.AD_HOC_CAMPAIGN_IS_NOT_UNIQUE);
    }
}
