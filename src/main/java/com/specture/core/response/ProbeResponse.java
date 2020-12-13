package com.specture.core.response;

import com.specture.core.enums.Status;
import com.specture.core.model.AdHocCampaign;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ProbeResponse {
    private String id;
    private TypeResponse type;
    private Status status;
    private String operator;
    private String comment;
    private int portCount;
    private String currentAdHocCampaignMark;
    private boolean assigned;
    private String probePurpose;
    private List<AdHocCampaign> adHocCampaigns;
    private List<ProbePortResponse> ports;
    private String siteName;
    private String siteAdvertisedId;
    private Long siteId;
    private Integer fixedPortNumber;
    private Integer mobilePortNumber;
}
