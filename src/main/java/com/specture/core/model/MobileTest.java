package com.specture.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Builder
@AllArgsConstructor
@Getter
@Document(indexName = "mobile_test_bh", type = "mobile_test", createIndex = false)
public class MobileTest {

    @Id
    private String openTestUuid;

    private String timestamp;
    private String measurementDate;
    private String operator;
    private String technology;
    private Integer download;
    private Integer upload;
    private Float ping;
    private Integer signal;
    private Float jitter;
    private Float packetLoss;
    private String probeId;

}
