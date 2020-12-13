package com.specture.core.mapper;

import com.specture.core.model.BasicTest;
import com.specture.core.response.BasicTestResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BasicTestMapper {
    @Mapping(target = "packageName", source = "packageNameStamp")
    BasicTestResponse basicTestToBasicTestResponse(BasicTest basicTest);
}
