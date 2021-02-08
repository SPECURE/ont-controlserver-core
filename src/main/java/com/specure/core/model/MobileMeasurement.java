package com.specure.core.model;


import com.specure.core.enums.MeasurementStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ToString
@Entity
@Table(name = "test")
public class MobileMeasurement {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private MeasurementStatus status;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "open_test_uuid")
    private String openTestUuid;

    @Column(name = "client_public_ip")
    private String clientPublicIp;

    @Column(name = "client_public_ip_anonymized")
    private String clientPublicIpAnonymized;

    @Column(name = "timezone")
    private String timezone;

    @Column(name = "client_time")
    private LocalDateTime clientTime;

    @Column(name = "public_ip_asn")
    private Long publicIpAsn;

    @Column(name = "public_ip_as_name")
    private String publicIpAsName;

    @Column(name = "country_asn")
    private String countryAsn;

    @Column(name = "public_ip_rdns")
    private String publicIpRdns;

    @Column(name = "last_sequence_number")
    private Integer lastSequenceNumber;
}
