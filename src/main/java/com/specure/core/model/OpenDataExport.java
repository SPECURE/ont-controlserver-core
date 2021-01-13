package com.specure.core.model;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@XmlAccessorType(XmlAccessType.FIELD)
public class OpenDataExport {

    @JsonRawValue
    private String openTestUuid;
    @JsonRawValue
    private String time;
    @JsonRawValue
    private String speedDownload;
    @JsonRawValue
    private String speedUpload;
    @JsonRawValue
    private String pingMedian;
    @JsonRawValue
    private String signalStrength;
    @JsonRawValue
    private String lteRsrp;
    @JsonRawValue
    private String networkType;
    @JsonRawValue
    private String catTechnology;
    @JsonRawValue
    private String provider;
    @JsonRawValue
    private String platform;
    @JsonRawValue
    private String numThreadsDl;
    @JsonRawValue
    private String numThreadsUl;
    @JsonRawValue
    private String ipProtocol;
}
