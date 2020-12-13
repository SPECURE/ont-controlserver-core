package com.specture.core.exception;

import com.specture.core.constant.ErrorMessage;

public class CampaignProbeAssignToSiteException extends RuntimeException {

    public CampaignProbeAssignToSiteException(String probeId) {
        super(String.format(ErrorMessage.CAMPAIGN_PROBE_ASSIGN_TO_SITE, probeId));
    }

}
