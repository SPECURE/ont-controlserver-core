package com.specture.core.request.measurement.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.specture.core.enums.Direction;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SpeedDetailRequest {
    private Direction direction;
    private Integer thread;
    private Long time;
    private Integer bytes;
}
