package com.specture.core.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class BasicTestResponse {
    private String openTestUuid;
    private String clientUuid;
    private String clientProvider;
    private String measurementDate;

    private String packageName;
    private String packageId;

    private String operator;
    private Integer download;
    private Integer upload;
    private Float ping;
    private Float jitter;
    private Integer signal;
    private Float packetLoss;
    private String probeId;
    private String probePort;
    private String serverType;
    private int graphHour;
    private String device;
    private String networkType;

    private String typeOfProbePort;

    private long siteId;
    private String siteName;
    private String siteAdvertisedId;

    private long measurementServerId;
    private String measurementServerName;

}
