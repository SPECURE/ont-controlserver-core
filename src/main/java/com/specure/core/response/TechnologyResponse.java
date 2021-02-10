package com.specure.core.response;

import com.specure.core.enums.Technology;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TechnologyResponse {
    private final Technology technology;
    private final String name;
    private final String type;
}
