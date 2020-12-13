package com.specture.core.exception;

import com.specture.core.constant.ErrorMessage;

import java.util.Set;

public class ProbeNotBelongToSiteException extends RuntimeException {

    public ProbeNotBelongToSiteException(Set<String> probeIds, Long siteId) {
        super(String.format(ErrorMessage.PROBE_NOT_BELONG_TO_SITE, probeIds, siteId));
    }

}
