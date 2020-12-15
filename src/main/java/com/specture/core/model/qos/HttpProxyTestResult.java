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
public class HttpProxyTestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String httpObjectiveUrl;
    private long httpResultDuration;
    private String httpResultHeader;
    private long durationNs;
    private int httpResultLength;
    private long qosTestUid;
    private long startTimeNs;
    private String httpResultHash;
    private int httpResultStatus;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "measurement_qos_id")
    private MeasurementQos measurementQos;
}
