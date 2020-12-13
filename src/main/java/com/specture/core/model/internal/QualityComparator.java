package com.specture.core.model.internal;

import com.specture.core.enums.UserExperienceQuality;

import java.util.Comparator;

public class QualityComparator implements Comparator<UserExperienceQuality> {
    public int compare(UserExperienceQuality first, UserExperienceQuality second) {
        return first.getScore() - second.getScore();
    }
}
