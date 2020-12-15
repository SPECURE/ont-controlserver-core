package com.specture.core.exception;

import com.specture.core.constant.ErrorMessage;

public class AdHocCampaignTimeLengthException extends RuntimeException {
    public AdHocCampaignTimeLengthException(long ms) {
        super(String.format(ErrorMessage.AD_HOC_CAMPAIGN_LENGTH, String.valueOf(ms / (1000 * 60))));
    }
}
