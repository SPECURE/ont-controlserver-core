package com.specture.core.response.settings;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AdvertisedSpeedOption {
    private boolean enabled;
    private int maxSpeedDownKbps;
    private int maxSpeedUpKbps;
    private int minSpeedDownKbps;
    private int minSpeedUpKbps;
    private String name;
    private double uid;
}
