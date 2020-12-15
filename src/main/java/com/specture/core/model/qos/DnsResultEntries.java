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
public class DnsResultEntries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dnsResultAddress;
    private String dnsResultTtl;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "dns_test_result_id")
    private DnsTestResult dnsTestResult;
}
