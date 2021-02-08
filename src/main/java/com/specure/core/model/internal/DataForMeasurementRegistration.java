package com.specure.core.model.internal;

import com.specure.core.enums.MeasurementType;
import com.specure.core.model.AdHocCampaign;
import com.specure.core.model.MeasurementServer;
import com.specure.core.model.Provider;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class DataForMeasurementRegistration {
    private MeasurementType measurementType;
    private MeasurementServer measurementServer;
    private String port;
    private String deviceOrProbeId;
    private Boolean isOnNet;
    private String clientUuid;
    private Provider provider;
    private AdHocCampaign adHocCampaign;
}
