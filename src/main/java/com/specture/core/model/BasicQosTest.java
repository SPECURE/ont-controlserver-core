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
@Document(indexName = "basic_qos_test_bh", type = "basic_qos_test", createIndex = false)
public class BasicQosTest {

    @Id
    private String openTestUuid;
    private String clientUuid;

    private String timestamp;
    private String measurementDate;
    private String operator;

    private long siteId;
    private String siteAdvertisedId;

    private long measurementServerId;
    private String measurementServerName;

    private String adHocCampaign;

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
