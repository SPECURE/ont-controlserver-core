package com.specture.core.exception;

import com.specture.core.constant.ErrorMessage;

public class AdHocCampaignProbeIsNotValidException extends RuntimeException {

    public AdHocCampaignProbeIsNotValidException(String probeId) {
        super(String.format(ErrorMessage.AD_HOC_CAMPAIGN_PROBE_IS_NOT_VALID, probeId));
    }
}
