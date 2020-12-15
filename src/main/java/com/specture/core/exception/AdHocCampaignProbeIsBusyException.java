package com.specture.core.exception;

import com.specture.core.constant.ErrorMessage;

public class AdHocCampaignProbeIsBusyException extends RuntimeException {

    public AdHocCampaignProbeIsBusyException(String probeId) {
        super(String.format(ErrorMessage.AD_HOC_CAMPAIGN_PROBE_IS_BUSY, probeId));
    }
}
