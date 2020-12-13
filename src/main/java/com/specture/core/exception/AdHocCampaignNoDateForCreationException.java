package com.specture.core.exception;

import com.specture.core.constant.ErrorMessage;

public class AdHocCampaignNoDateForCreationException extends RuntimeException {
    public AdHocCampaignNoDateForCreationException() {
        super(ErrorMessage.AD_HOC_CAMPAIGN_NO_DATE_FOR_CREATION);
    }
}
