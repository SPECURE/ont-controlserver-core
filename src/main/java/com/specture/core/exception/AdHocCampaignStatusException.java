package com.specture.core.exception;

import com.specture.core.constant.ErrorMessage;

public class AdHocCampaignStatusException extends RuntimeException {

    public AdHocCampaignStatusException(String badAdHocCampaignStatus) {
        super(String.format(ErrorMessage.BAD_CAMPAIGN_STATUS, badAdHocCampaignStatus));
    }
}
