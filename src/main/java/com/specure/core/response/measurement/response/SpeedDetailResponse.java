package com.specure.core.response.measurement.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.specure.core.enums.Direction;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SpeedDetailResponse {
    private Direction direction;
    private Integer thread;
    private Long time;
    private Integer bytes;
}
