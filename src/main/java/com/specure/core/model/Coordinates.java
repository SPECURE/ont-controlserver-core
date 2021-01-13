package com.specure.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Builder
@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Coordinates {

    @Column(name = "latitude")
    private Float lat;

    @Column(name = "longitude")
    private Float lng;

}
