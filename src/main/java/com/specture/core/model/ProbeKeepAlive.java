package com.specture.core.model;

import com.specture.core.enums.ProbePortStatus;
import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ProbeKeepAlive extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String probeId;

    private String port;

    private Long liveTime;

    private String testedIp;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "ad_hoc_campaign_id")
    private AdHocCampaign adHocCampaign;

    @OneToOne
    @JoinColumn(name = "site_id")
    private Site site;

    @OneToOne
    @JoinColumn(name = "package_id")
    private Package aPackage;

    @Enumerated(EnumType.STRING)
    private ProbePortStatus status;

}
