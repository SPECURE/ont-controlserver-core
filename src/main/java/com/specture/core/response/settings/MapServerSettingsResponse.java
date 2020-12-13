package com.specture.core.response.settings;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MapServerSettingsResponse {
    private String host;
    private int port;
    private boolean ssl;
}
