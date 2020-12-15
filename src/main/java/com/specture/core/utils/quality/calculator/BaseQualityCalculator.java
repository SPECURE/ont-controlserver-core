package com.specture.core.utils.quality.calculator;

import com.specture.core.enums.UserExperienceQuality;
import com.specture.core.exception.WrongNameOfUserExperienceParameterException;
import com.specture.core.model.internal.ParameterCondition;
import com.specture.core.model.internal.QualityComparator;
import com.specture.core.response.userexperience.UserExperienceParameter;

import java.util.List;

import static com.specture.core.enums.UserExperienceQuality.*;

public abstract class BaseQualityCalculator {

    protected UserExperienceQuality getQuality(double value, ParameterCondition excellent, ParameterCondition good, ParameterCondition moderate, ParameterCondition poor, ParameterCondition bad) {
        if (excellent.isTrue(value)) return EXCELLENT;
        if (good.isTrue(value)) return GOOD;
        if (moderate.isTrue(value)) return MODERATE;
        if (poor.isTrue(value)) return POOR;
        return BAD;
    }

    protected double getValueByName(String name, List<UserExperienceParameter> parameters) throws RuntimeException {
        return parameters
                .stream()
                .filter(e -> e.getField().equals(name))
                .findFirst()
                .orElseThrow(() -> new WrongNameOfUserExperienceParameterException(name))
                .getValue();
    }

    protected UserExperienceQuality getWorst(List<UserExperienceQuality> allQualityMarks) {
        allQualityMarks.sort(new QualityComparator());
        return allQualityMarks.get(0);
    }
}
