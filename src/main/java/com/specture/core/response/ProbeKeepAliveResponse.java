package com.specture.core.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.specture.core.enums.ProbePortStatus;
import lombok.Builder;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.sql.Timestamp;

@Builder
@Data
public class ProbeKeepAliveResponse {

    private Long id;

    private String probeId;

    private String port;

    private Long liveTime;

    private String testedIp;

    private String site;

    @JsonProperty("package")
    private PackageResponse aPackage;

    @Enumerated(EnumType.STRING)
    private ProbePortStatus status;

    private Timestamp createdDate;

}
