package com.specture.core.mapper;

import com.specture.core.model.stats.AverageSpeeds;
import com.specture.core.response.stats.AverageSpeedsResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AverageSpeedMapper {
    AverageSpeedsResponse averageSpeedToAverageSpeedResponse(AverageSpeeds average);
}
