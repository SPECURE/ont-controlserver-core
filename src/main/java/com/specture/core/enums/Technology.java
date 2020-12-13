package com.specture.core.enums;

import lombok.Getter;

import static com.specture.core.enums.TechnologyType.FIXED;
import static com.specture.core.enums.TechnologyType.MOBILE;

@Getter
public enum Technology {
    ADSL("ADSL", FIXED), FIBER("Fiber", FIXED), FOUR_G_LTE("4G LTE", MOBILE), FIVE_G("5G", MOBILE);

    private final String name;
    private final TechnologyType type;

    Technology(String name, TechnologyType type) {
        this.name = name;
        this.type = type;
    }
}
