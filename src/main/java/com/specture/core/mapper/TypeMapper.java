package com.specture.core.mapper;

import com.specture.core.enums.ProbeType;
import com.specture.core.response.TypeResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TypeMapper {

    default TypeResponse typeToTypeResponse(ProbeType type) {
        if (type == null)
            return null;

        return TypeResponse.builder()
                .name(type.getName())
//                .url(type.getUrl())
//                .mobilePortNumber(type.getMobilePortsNumber())
//                .fixedPortNumber(type.getFixedPortsNumber())
                .build();
    }

}
