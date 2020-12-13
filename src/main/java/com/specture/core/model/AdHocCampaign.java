package com.specture.core.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.specture.core.model.Provider;
import com.specture.core.enums.AdHocCampaignStatus;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;


@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class AdHocCampaign extends BaseEntity {
    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    private AdHocCampaignStatus status;

    private String location;
    @Embedded
    private Coordinates coordinates;

    private Timestamp start;
    private Timestamp finish;

    @JsonBackReference
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "probe_id")
    private Probe probe;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "provider_id")
    private Provider provider;
}
