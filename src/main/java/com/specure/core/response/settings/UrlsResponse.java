package com.specure.core.response.settings;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UrlsResponse {
    private String controlIpv4Only;
    private String controlIpv6Only;
    private String openDataPrefix;
    private String statistics;
    private String urlIpv4Check;
    private String urlIpv6Check;
    private String urlShare;
    private String urlMapServer;
}
