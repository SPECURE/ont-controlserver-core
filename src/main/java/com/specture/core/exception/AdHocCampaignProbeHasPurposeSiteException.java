package com.specture.core.exception;

import com.specture.core.constant.ErrorMessage;

public class AdHocCampaignProbeHasPurposeSiteException extends RuntimeException {

    public AdHocCampaignProbeHasPurposeSiteException(String probeId) {
        super(String.format(ErrorMessage.AD_HOC_CAMPAIGN_PROBE_HAS_PURPOSE_SITE, probeId));
    }
}
