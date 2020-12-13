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
public class OnLineGamingQualityCalculatorImpl extends BaseQualityCalculator implements QualityCalculator {
    @Override
    public UserExperienceCategory getCategory() {
        return UserExperienceCategory.ONLINE_GAMING;
    }

    @Override
    public UserExperienceQuality calculate(List<UserExperienceParameter> parameters) {
        double param;
        ArrayList<UserExperienceQuality> onlineGamingMarks = new ArrayList<>();
        param = getValueByName(DOWNLOAD_METRIC_PARAMETER, parameters);
        onlineGamingMarks.add(getQuality(
                param,
                new ParameterCondition(MORE_OR_EQUAL, 10000),
                new ParameterCondition(MORE_OR_EQUAL, 5000),
                new ParameterCondition(MORE_OR_EQUAL, 3000),
                new ParameterCondition(MORE_OR_EQUAL, 1000),
                new ParameterCondition(LESS, 1000)
        ));

        param = getValueByName(UPLOAD_METRIC_PARAMETER, parameters);
        onlineGamingMarks.add(getQuality(
                param,
                new ParameterCondition(MORE_OR_EQUAL, 5000),
                new ParameterCondition(MORE_OR_EQUAL, 3000),
                new ParameterCondition(MORE_OR_EQUAL, 1000),
                new ParameterCondition(MORE_OR_EQUAL, 500),
                new ParameterCondition(LESS, 500)
        ));
//        param = getValueByName(JITTER_METRIC_PARAMETER, parameters);
//        onlineGamingMarks.add(getQuality(
//                param,
//                new ParameterCondition(LESS, 10),
//                new ParameterCondition(LESS, 30),
//                new ParameterCondition(LESS, 50),
//                new ParameterCondition(LESS, 100),
//                new ParameterCondition(MORE_OR_EQUAL, 100)
//        ));
        param = getValueByName(PING_METRIC_PARAMETER, parameters);
        onlineGamingMarks.add(getQuality(
                param,
                new ParameterCondition(LESS, 50),
                new ParameterCondition(LESS, 100),
                new ParameterCondition(LESS, 150),
                new ParameterCondition(LESS, 200),
                new ParameterCondition(MORE_OR_EQUAL, 200)
        ));
        return getWorst(onlineGamingMarks);
    }
}
