package com.specure.core.model.qos;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.specure.core.model.AdHocCampaign;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MeasurementQos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String testToken;
    private String clientUuid;
    private String openTestUuid;
    private Timestamp time;
    private String clientVersion;

    private String clientName;
    private String clientLanguage;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "ad_hoc_campaign_id")
    private AdHocCampaign adHocCampaign;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "measurementQos")
    @JsonManagedReference
    private List<VoipTestResult> voipTestResults;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "measurementQos")
    @JsonManagedReference
    private List<TcpTestResult> tcpTestResults;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "measurementQos")
    @JsonManagedReference
    private List<UdpTestResult> udpTestResults;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "measurementQos")
    @JsonManagedReference
    private List<HttpProxyTestResult> httpProxyTestResults;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "measurementQos")
    @JsonManagedReference
    private List<NonTransparentProxyTestResult> nonTransparentProxyTestResults;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "measurementQos")
    @JsonManagedReference
    private List<DnsTestResult> dnsTestResults;
}
