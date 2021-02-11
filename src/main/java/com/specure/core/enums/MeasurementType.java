package com.specure.core.enums;

import lombok.Getter;

@Getter
public enum MeasurementType {
    RMBTws("RMBTws", ServerTechForMeasurement.WS_TECH),
    RMBT("RMBT", ServerTechForMeasurement.RMBT_TECH),
    QoS("QoS", ServerTechForMeasurement.QOS_TECH),
    HTTP("HTTP", ServerTechForMeasurement.QOS_TECH);

    private final String name;
    private final ServerTechForMeasurement serverTechForMeasurement;

    MeasurementType(
            String name,
            ServerTechForMeasurement serverTechForMeasurement
    ) {
        this.name = name;
        this.serverTechForMeasurement = serverTechForMeasurement;
    }
}
