package com.specture.core.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PackagePortResponse {
    private Long id;
    private String advertisedName;
    private String port;
    private String packageType;
}
