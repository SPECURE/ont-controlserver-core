package com.specure.core.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SettingRequest {
    private String language;
    private String name;
    private boolean termsAndConditionsAccepted;
    private String timezone;
    private String type;
    private String versionCode;
    private String versionName;
}
