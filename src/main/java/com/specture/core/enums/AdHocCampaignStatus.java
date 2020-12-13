package com.specture.core.enums;

import com.specture.core.exception.AdHocCampaignStatusException;
import lombok.Getter;

@Getter
public enum AdHocCampaignStatus {
    RUNNING("Running"),
    NOT_STARTED("Not Started"),
    FINISHED("Finished");
    private final String value;

    AdHocCampaignStatus(String value) {
        this.value = value;
    }

    public static AdHocCampaignStatus fromString(String statusName) {
        for (AdHocCampaignStatus b : AdHocCampaignStatus.values()) {
            if (b.getValue().equals(statusName)) {
                return b;
            }
        }
        throw new AdHocCampaignStatusException(statusName);
    }

}
