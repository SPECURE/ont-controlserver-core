package com.specure.core.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Site extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String advertisedId;

    private String address;

    @Embedded
    private Coordinates coordinates;

    @Embedded
    private MeasurementIsActive measurement;

    @JsonManagedReference
    @OneToMany(mappedBy = "site")
    List<Probe> probes = new ArrayList<>();

    public void setProbes(List<Probe> probes) {
        this.probes.forEach(l -> l.setSite(null));
        probes.forEach(probe -> probe.setSite(this));
        this.probes = probes;
    }

    private boolean visibleOnPublicPortal;
}
