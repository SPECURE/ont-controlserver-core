package com.specture.core.response.userexperience;

import com.specture.core.enums.UserExperienceCategory;
import com.specture.core.enums.UserExperienceQuality;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserExperienceMetric {
    private UserExperienceQuality quality;
    private UserExperienceCategory category;
}
