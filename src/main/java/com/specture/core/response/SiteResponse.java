package com.specture.core.response;

import com.specture.core.model.Coordinates;
import com.specture.core.model.MeasurementIsActive;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class SiteResponse {

    private Long id;

    private String advertisedId;

    private String name;

    private String address;

    private Coordinates coordinates;

    private MeasurementIsActive measurement;

    private List<ProbeResponse> probes;

}
