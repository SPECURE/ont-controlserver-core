package com.specure.core.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.specure.core.enums.MeasurementStatus;
import com.specure.core.enums.Platform;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String openTestUuid;
    private String clientUuid;
    private Integer testSlot;
    private String token;
    private Timestamp time;
    private Integer speedDownload;
    private Integer speedUpload;
    private Long pingMedian;
    private Integer signalStrength;
    private Integer lte_rsrp;
    private Integer lte_rsrq;

    private String device; // as probe_id
    private String tag; // as probe_port,
    private Integer networkType;
    private String networkOperator; // provider
    private String clientProvider;

    private String ipAddress;

    private String voip_result_jitter; // strange type for number
    private String voip_result_packet_loss; // strange type for number

    private Long measurementServerId;

    //new fields

    private String clientLanguage;
    private String clientName;
    private String clientVersion;

    private String model;
    @Enumerated(EnumType.STRING)
    private Platform platform;
    private String product;

    private Long testBytesDownload;
    private Long testBytesUpload;

    private Long testNsecDownload;
    private Long testNsecUpload;
    private Integer testNumThreads;
    private Integer numThreadsUl;
    private Long testPingShortest;
    private Integer testSpeedDownload;
    private Integer testSpeedUpload;

    private String timezone;
    private String type;
    private String versionCode;

    private String serverType;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "ad_hoc_campaign_id")
    private AdHocCampaign adHocCampaign;

    @Enumerated(EnumType.STRING)
    private MeasurementStatus status;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "measurement")
    @JsonManagedReference
    private List<SpeedDetail> speedDetail;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "measurement")
    @JsonManagedReference
    private List<GeoLocation> geoLocations;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "measurement")
    @JsonManagedReference
    private List<Ping> pings;

    public String getTimeStampToElasticIso() {
        if (this.time != null) {
            var instant = this.time.toInstant();
            DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
            return  formatter.format(instant);
        }
        return null;
    }
    public Integer getHourOfMeasurement() {
        if (this.time != null) {
            var instant = this.time.toInstant();
            DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
            return  instant.atZone(ZoneOffset.UTC).getHour();
        }
        return null;
    }
}
