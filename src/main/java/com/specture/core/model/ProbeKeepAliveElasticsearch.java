package com.specture.core.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

@Data
@Builder
@Document(indexName = "keep_alive", type = "_doc", createIndex = false)
public class ProbeKeepAliveElasticsearch {
    private Long id;
    private String probeId;
    private String port;
    private Long liveTime;
    private String testedIp;
    private String status;

    private String site;
    private Long siteId;

    private String provider;
    private Long providerId;

    private String packageName;
    private Long packageId;

    private String adHocCampaign;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date createdDate;
    private String timestamp;

    private String tenant;
}
