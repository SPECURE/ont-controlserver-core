package com.specure.core.request.measurement.qos.request;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Data
@JsonTypeName("udp")
@EqualsAndHashCode(callSuper = false)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Getter
@Setter
public class UdpTestResultRequest extends TestResult {
    private int udpResultOutNumPackets;
    private long durationNs;
    private int udpResultOutResponseNumPackets;
    private int udpObjectiveOutNumPackets;
    private String udpResultOutPacketLossRate;
    private int udpObjectiveOutPort;
    private long udpObjectiveDelay;
    private long udpObjectiveTimeout;
    private int qosTestUid;
    private long startTimeNs;
}
