package com.specure.core.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Builder
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Getter
public class SignalMeasurementResponse {

    private final String testUuid;

    private final String userUuid;

    private final String testType;

    private final String technology;

    private final Integer duration;

    private final ZonedDateTime time;

}
