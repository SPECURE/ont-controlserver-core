package com.specture.core.exception;

import com.specture.core.constant.ErrorMessage;

public class MoreThenOneCampaignForOneProbeSimultaneouslyException extends RuntimeException {

    public MoreThenOneCampaignForOneProbeSimultaneouslyException(String probeId) {
        super(String.format(ErrorMessage.SEVERAL_CAMPAIGN_FOR_ONE_PROBE_SIMULTANEOUSLY, probeId));
    }

}
