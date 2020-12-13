package com.specture.core.mapper;

import com.specture.core.response.BasicQosTestResponse;
import com.specture.core.model.BasicQosTest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BasicQosTestMapper {
    BasicQosTestResponse basicQosTestToBasicQosTestResponse(BasicQosTest basicQosTest);
}
