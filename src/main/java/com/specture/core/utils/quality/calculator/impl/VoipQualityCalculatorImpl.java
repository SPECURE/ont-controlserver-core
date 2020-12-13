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

import static com.specture.core.constant.PropertyName.*;
import static com.specture.core.enums.Condition.LESS;
import static com.specture.core.enums.Condition.MORE_OR_EQUAL;

@Component
public class VoipQualityCalculatorImpl extends BaseQualityCalculator implements QualityCalculator {
    @Override
    public UserExperienceCategory getCategory() {
        return UserExperienceCategory.VOIP;
    }

    @Override
    public UserExperienceQuality calculate(List<UserExperienceParameter> parameters) {
        ArrayList<UserExperienceQuality> voipMarks = new ArrayList<>();
        voipMarks.add(getQuality(
                getValueByName(DOWNLOAD_METRIC_PARAMETER, parameters),
                new ParameterCondition(MORE_OR_EQUAL, 1500),
                new ParameterCondition(MORE_OR_EQUAL, 1000),
                new ParameterCondition(MORE_OR_EQUAL, 500),
                new ParameterCondition(MORE_OR_EQUAL, 200),
                new ParameterCondition(LESS, 200)
        ));
        voipMarks.add(getQuality(
                getValueByName(UPLOAD_METRIC_PARAMETER, parameters),
                new ParameterCondition(MORE_OR_EQUAL, 1500),
                new ParameterCondition(MORE_OR_EQUAL, 1000),
                new ParameterCondition(MORE_OR_EQUAL, 500),
                new ParameterCondition(MORE_OR_EQUAL, 200),
                new ParameterCondition(LESS, 200)
        ));
//        voipMarks.add(getQuality(
//                getValueByName(JITTER_METRIC_PARAMETER, parameters),
//                new ParameterCondition(LESS, 10),
//                new ParameterCondition(LESS, 30),
//                new ParameterCondition(LESS, 50),
//                new ParameterCondition(LESS, 100),
//                new ParameterCondition(MORE_OR_EQUAL, 100)
//        ));
        voipMarks.add(getQuality(
                getValueByName(PING_METRIC_PARAMETER, parameters),
                new ParameterCondition(LESS, 50),
                new ParameterCondition(LESS, 100),
                new ParameterCondition(LESS, 150),
                new ParameterCondition(LESS, 200),
                new ParameterCondition(MORE_OR_EQUAL, 200)
        ));
        return getWorst(voipMarks);
    }
}
