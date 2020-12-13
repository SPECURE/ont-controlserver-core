package com.specture.core.model.campaign;

import com.specture.core.model.AdHocCampaign;
import com.specture.core.model.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AdHocCampaignDowntime extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Timestamp start;
    private Timestamp finish;

    private Long duration;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "ad_hoc_campaign_id")
    private AdHocCampaign adHocCampaign;

    @PrePersist
    protected void preInsert() {
        super.preInsert();
        if (!Objects.isNull(start) && !Objects.isNull(finish)) {
            duration = finish.getTime() - start.getTime();
        }
    }

    @PreUpdate
    protected void preUpdate() {
        super.preUpdate();
        if (!Objects.isNull(start) && !Objects.isNull(finish)) {
            duration = finish.getTime() - start.getTime();
        }
    }
}
