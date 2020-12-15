package com.specture.core.model;

import com.specture.core.model.Provider;
import lombok.*;

import javax.persistence.*;


@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MeasurementServer extends BaseEntity{
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
}
