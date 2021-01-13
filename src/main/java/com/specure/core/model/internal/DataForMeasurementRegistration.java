package com.specure.core.model.internal;

import com.specure.core.model.Provider;
import com.specure.core.enums.ClientType;
import com.specure.core.model.AdHocCampaign;
import com.specure.core.model.MeasurementServer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class DataForMeasurementRegistration {
    private ClientType clientType;
    private MeasurementServer measurementServer;
    private String port;
    private String deviceOrProbeId;
    private Boolean isOnNet;
    private String clientUuid;
    private Provider provider;
    private AdHocCampaign adHocCampaign;
}
