package com.specture.core.model;

import com.specture.core.enums.MeasurementStatus;
import com.specture.core.enums.Platform;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Builder
@Entity(name = "measurement")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class OpenData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String openTestUuid;
    private Timestamp time;
    private Integer speedDownload;
    private Integer speedUpload;
    private Long pingMedian;
    private Integer signalStrength;
    private Integer lte_rsrp;
    private Integer networkType;
    private String networkOperator;
    private String clientProvider;
    @Enumerated(EnumType.STRING)
    private Platform platform;
    private Integer testNumThreads;
    private Integer numThreadsUl;
    private String ipAddress;
    @Enumerated(EnumType.STRING)
    private MeasurementStatus status;

//    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "measurement", targetEntity = OpenDataGeoLocation.class)
//    @Fetch(value = FetchMode.SUBSELECT)
//    private List<OpenDataGeoLocation> geoLocations = new ArrayList<>();

}
