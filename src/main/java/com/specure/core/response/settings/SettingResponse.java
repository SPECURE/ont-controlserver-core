package com.specure.core.response.settings;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SettingResponse {
    private List<AdvertisedSpeedOption> advertisedSpeedOption;
    private HistorySettingsResponse history;
    private MapServerSettingsResponse mapServer;

    @JsonProperty("qostesttype_desc")
    private List<QosMeasurementTypeDescription> qosTestTypeDesc;

    private SurveySettingsResponse surveySettings;
    private String uuid;
    private UrlsResponse urls;
    private VersionsResponse versions;
}
