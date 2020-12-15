package com.specture.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Builder
@AllArgsConstructor
@Getter
@Setter
@Document(indexName = "basic_test_bh", type = "basic_test", createIndex = false)
public class BasicTest {

    @Id
    private String openTestUuid;
    private String clientUuid;
    private String clientProvider;

    private String timestamp;
    private String measurementDate;
    private String operator;
    private Integer download;
    private Integer upload;
    private Float ping;
    private Float jitter;
    private Integer signal;
    private Float packetLoss;

    private String probeId;
    private String probePort;
    private String packageId;
    private String packageNameStamp;
    private String device;
    private String networkType;

    private long siteId;
    private String siteName;
    private String siteAdvertisedId;

    private String adHocCampaign;

    private long measurementServerId;
    private String measurementServerName;

    private String typeOfProbePort;

    private String serverType;
    private int graphHour;

}
