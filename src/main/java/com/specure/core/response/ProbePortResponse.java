package com.specure.core.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.specure.core.enums.PortType;
import com.specure.core.enums.ProbePortStatus;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Builder
@Data
public class ProbePortResponse {
    private Long id;
    private String name;
    private PortType type;
    private ProbePortStatus status;

    @JsonProperty("package")
    private PackageResponse aPackage;

    private String site;
    private String probe;

    private Timestamp timestamp;
}
