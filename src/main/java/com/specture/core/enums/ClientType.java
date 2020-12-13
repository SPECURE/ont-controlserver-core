package com.specture.core.enums;

import lombok.Getter;

@Getter
public enum ClientType {
    RMBTws("RMBTws", ServerTechForMeasurement.WS_TECH),
    RMBT("RMBT", ServerTechForMeasurement.RMBT_TECH);

    private final String name;
    private final ServerTechForMeasurement serverTechForMeasurement;

    ClientType(
            String name,
            ServerTechForMeasurement serverTechForMeasurement
    ) {
        this.name = name;
        this.serverTechForMeasurement = serverTechForMeasurement;
    }
}
