package com.specture.core.exception;

import com.specture.core.constant.ErrorMessage;

public class AdHocCampaignDowntimeCurrentSignalNullException extends RuntimeException {

    public AdHocCampaignDowntimeCurrentSignalNullException() {
        super(ErrorMessage.AD_HOC_CAMPAIGN_DOWNTIME_CURRENT_NULL);
    }
}
