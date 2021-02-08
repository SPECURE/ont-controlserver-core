package com.specure.core.model;

import com.specure.core.enums.MeasurementType;
import lombok.*;

import javax.persistence.*;
import java.util.Set;


@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MeasurementServer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String webAddress;
    private String secretKey;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "provider")
    private Provider provider;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "measurementServerDescription")
    private MeasurementServerDescription measurementServerDescription;

    @Column(name = "server_type")
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = MeasurementType.class)
    @CollectionTable(name = "measurement_server_types")
    private Set<MeasurementType> serverType;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "selectable")
    private Boolean selectable;

    @Column(name = "uuid")
    private String uuid;
}
