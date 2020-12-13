package com.specture.core.exception;

import com.specture.core.constant.ErrorMessage;

public class AdHocCampaignStartTimeException extends RuntimeException {
    public AdHocCampaignStartTimeException() {
        super(ErrorMessage.AD_HOC_CAMPAIGN_START);
    }
}
