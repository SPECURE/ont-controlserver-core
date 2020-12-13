package com.specture.core.model;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@Entity(name = "geo_location")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OpenDataGeoLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "measurement_id")
    private OpenData measurement;

    private Double geoLat;
    private Double geoLong;
    private Double accuracy;
}
