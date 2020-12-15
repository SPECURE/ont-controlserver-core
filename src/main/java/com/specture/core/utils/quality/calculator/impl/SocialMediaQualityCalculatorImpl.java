package com.specture.core.utils.quality.calculator.impl;

import com.specture.core.enums.UserExperienceCategory;
import com.specture.core.enums.UserExperienceQuality;
import com.specture.core.model.internal.ParameterCondition;
import com.specture.core.response.userexperience.UserExperienceParameter;
import com.specture.core.utils.quality.calculator.BaseQualityCalculator;
import com.specture.core.utils.quality.calculator.QualityCalculator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.specture.core.constant.PropertyName.DOWNLOAD_METRIC_PARAMETER;
import static com.specture.core.constant.PropertyName.UPLOAD_METRIC_PARAMETER;
import static com.specture.core.enums.Condition.LESS;
import static com.specture.core.enums.Condition.MORE_OR_EQUAL;

@Component
public class SocialMediaQualityCalculatorImpl extends BaseQualityCalculator implements QualityCalculator {
    @Override
    public UserExperienceCategory getCategory() {
        return UserExperienceCategory.SOCIAL_MEDIA;
    }

    @Override
    public UserExperienceQuality calculate(List<UserExperienceParameter> parameters) {
        ArrayList<UserExperienceQuality> socialMediaMarks = new ArrayList<>();
        socialMediaMarks.add(getQuality(
                getValueByName(DOWNLOAD_METRIC_PARAMETER, parameters),
                new ParameterCondition(MORE_OR_EQUAL, 10000),
                new ParameterCondition(MORE_OR_EQUAL, 5000),
                new ParameterCondition(MORE_OR_EQUAL, 1500),
                new ParameterCondition(MORE_OR_EQUAL, 500),
                new ParameterCondition(LESS, 500)
        ));
        socialMediaMarks.add(getQuality(
                getValueByName(UPLOAD_METRIC_PARAMETER, parameters),
                new ParameterCondition(MORE_OR_EQUAL, 5000),
                new ParameterCondition(MORE_OR_EQUAL, 3000),
                new ParameterCondition(MORE_OR_EQUAL, 1500),
                new ParameterCondition(MORE_OR_EQUAL, 1000),
                new ParameterCondition(LESS, 1000)
        ));
        return getWorst(socialMediaMarks);
    }
}
