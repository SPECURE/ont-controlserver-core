package com.specture.core.response.settings;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SurveySettingsResponse {
    private long dateStarted;
    private boolean isActiveService;
    private String surveyUrl;
}
