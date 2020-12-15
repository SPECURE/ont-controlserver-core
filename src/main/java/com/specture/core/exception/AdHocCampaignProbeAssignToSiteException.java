package com.specture.core.exception;

import com.specture.core.constant.ErrorMessage;

public class AdHocCampaignProbeAssignToSiteException extends RuntimeException {

    public AdHocCampaignProbeAssignToSiteException(String probeId) {
        super(String.format(ErrorMessage.AD_HOC_CAMPAIGN_PROBE_IS_ASSIGNED_TO_SITE, probeId));
    }
}
