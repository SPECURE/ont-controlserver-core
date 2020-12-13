package com.specture.core.model.internal;

import com.specture.core.model.Provider;
import com.specture.core.enums.ClientType;
import com.specture.core.model.AdHocCampaign;
import com.specture.core.model.MeasurementServer;
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
