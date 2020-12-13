package com.specture.core.utils.quality.calculator;

import com.specture.core.enums.UserExperienceCategory;
import com.specture.core.enums.UserExperienceQuality;
import com.specture.core.response.userexperience.UserExperienceParameter;

import java.util.List;

public interface QualityCalculator {
    UserExperienceCategory getCategory();
    UserExperienceQuality calculate(List<UserExperienceParameter> parameters);
}
