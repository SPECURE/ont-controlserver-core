package com.specure.core.model.qos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.specure.core.request.measurement.qos.request.TestResult;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DnsTestResult extends TestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private long dnsObjectiveTimeout;
    private long qosTestUid;
    private long startTimeNs;
    private String dnsObjectiveDnsRecord;
    private long durationNs;
    private String dnsObjectiveHost;
    private String dnsObjectiveResolver;
    private String dnsResultInfo;
    private String dnsResultStatus;
    private long dnsResultDuration;
    private int dnsResultEntriesFound;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "measurement_qos_id")
    private MeasurementQos measurementQos;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dnsTestResult")
    @JsonManagedReference
    private List<DnsResultEntries> dnsResultEntries;
}
