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
public class TcpTestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tcpResultOut;
    private Long tcpObjectiveTimeout;
    private Long durationNs;
    private String tcpResultOutResponse;
    private int tcpObjectiveOutPort;
    private Long qosTestUid;
    private Long startTimeNs;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "measurement_qos_id")
    private MeasurementQos measurementQos;
}
