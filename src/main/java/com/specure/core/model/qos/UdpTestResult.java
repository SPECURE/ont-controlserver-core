package com.specure.core.model.qos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UdpTestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int udpResultOutNumPackets;
    private long durationNs;
    private int udpResultOutResponseNumPackets;
    private int udpObjectiveOutNumPackets;
    private String udpResultOutPacketLossRate;
    private int udpObjectiveOutPort;
    private long udpObjectiveDelay;
    private long udpObjectiveTimeout;
    private long qosTestUid;
    private long startTimeNs;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "measurement_qos_id")
    private MeasurementQos measurementQos;
}
