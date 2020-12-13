package com.specture.core.mapper;

import com.specture.core.enums.Technology;
import com.specture.core.response.TechnologyResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TechnologyMapper {

    default TechnologyResponse typeToTypeResponse(Technology technology) {

        return TechnologyResponse.builder()
                .technology(technology)
                .name(technology.getName())
                .type(technology.getType().name())
                .build();
    }

}