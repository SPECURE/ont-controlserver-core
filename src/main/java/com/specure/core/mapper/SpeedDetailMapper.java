package com.specure.core.mapper;

import com.specure.core.model.SpeedDetail;
import com.specure.core.request.measurement.request.SpeedDetailRequest;
import com.specure.core.response.measurement.response.SpeedDetailResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpeedDetailMapper {
    SpeedDetail speedDetailRequestToSpeedDetail(SpeedDetailRequest speedDetailRequest);
    SpeedDetailResponse speedDetailToSpeedDetailResponse(SpeedDetail speedDetail);
}
