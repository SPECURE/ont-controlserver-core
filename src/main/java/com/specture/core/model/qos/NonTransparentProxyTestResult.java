package com.specture.core.model.qos;

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
public class NonTransparentProxyTestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nonTransparentProxyResult;
    private String nonTransparentProxyObjectiveRequest;
    private long nonTransparentProxyObjectiveTimeout;
    private int nonTransparentProxyObjectivePort;
    private String nonTransparentProxyResultResponse;
    private long qosTestUid;
    private long durationNs;
    private long startTimeNs;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "measurement_qos_id")
    private MeasurementQos measurementQos;
}
