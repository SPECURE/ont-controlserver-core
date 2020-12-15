package com.specture.core.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.specture.core.enums.PortType;
import com.specture.core.enums.ProbePortStatus;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Builder(toBuilder = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ProbePort extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private PortType type;

    @Enumerated(EnumType.STRING)
    private ProbePortStatus status;

    private Timestamp timestamp;

    @ManyToOne
    @JoinColumn(name = "probe_id")
    @JsonBackReference
    private Probe probe;

    @ManyToOne
    @JoinColumn(name = "package_id")
    @JsonBackReference
    private Package aPackage;
}
