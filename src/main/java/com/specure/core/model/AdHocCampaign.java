package com.specure.core.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.specure.core.enums.AdHocCampaignStatus;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;


@Builder
@Entity
@Component
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
    @ManyToOne()
    @JoinColumn(name = "probe_id")
    private Probe probe;

    @ManyToOne()
    @JoinColumn(name = "provider_id")
    private Provider provider;

    private String description;
}
