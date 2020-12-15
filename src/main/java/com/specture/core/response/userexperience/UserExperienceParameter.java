package com.specture.core.response.userexperience;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserExperienceParameter {
    private String field;
    private Double value;
    private String unit;
    private int order;
}
