package com.specture.core.mapper;

import com.specture.core.model.SpeedDetail;
import com.specture.core.request.measurement.request.SpeedDetailRequest;
import com.specture.core.response.measurement.response.SpeedDetailResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpeedDetailMapper {
    SpeedDetail speedDetailRequestToSpeedDetail(SpeedDetailRequest speedDetailRequest);
    SpeedDetailResponse speedDetailToSpeedDetailResponse(SpeedDetail speedDetail);
}
