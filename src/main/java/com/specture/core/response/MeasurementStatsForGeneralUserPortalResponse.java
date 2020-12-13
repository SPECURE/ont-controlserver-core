package com.specture.core.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MeasurementStatsForGeneralUserPortalResponse {
    private Long today;
    private Long thisWeek;
    private Long thisMonth;
    private Long thisYear;

}
