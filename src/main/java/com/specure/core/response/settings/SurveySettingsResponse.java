package com.specure.core.response.settings;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SurveySettingsResponse {
    private long dateStarted;
    @JsonProperty(value = "active_service")
    private boolean isActiveService;
    private String surveyUrl;
}
