package com.specure.core.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class BasicQosTestResponse {

    private String openTestUuid;
    private String clientUuid;

    private String timestamp;
    private String measurementDate;
    private String operator;

    private long siteId;
    private String siteAdvertisedId;

    private long measurementServerId;
    private String measurementServerName;

    private Float dns;
    private Float httpProxy;
    private Float nonTransparentProxy;
    private Float tcp;
    private Float udp;
    private Float voip;
    private Float overallQos;

    private String probeId;
    private String probePort;
    private String packageAdvertisedName;
    private String packageId;

    private String clientVersion;
    private String clientName;
    private String clientLanguage;

    private String typeOfProbePort;
    private String serverType;
    private int graphHour;
}
