package com.specure.core.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.specure.core.enums.PackageDescription;
import com.specure.core.enums.PackageType;
import com.specure.core.enums.PortType;
import com.specure.core.enums.Technology;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Package extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String advertisedName;

    @ManyToOne
    @JoinColumn(name = "provider")
    private Provider provider;

    @Enumerated(EnumType.STRING)
    private PackageDescription packageDescription;

    @Enumerated(EnumType.STRING)
    private PackageType packageType;

    @Enumerated(EnumType.STRING)
    private PortType readyForPort;

    @Enumerated(EnumType.STRING)
    private Technology technology;

    private Long threshold;
    private Long download;
    private Long upload;
    private Long throttleSpeedDownload;
    private Long throttleSpeedUpload;

    @OneToMany(mappedBy = "aPackage")
    @JsonManagedReference
    private List<ProbePort> probePorts;
}
