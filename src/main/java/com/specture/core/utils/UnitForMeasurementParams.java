package com.specture.core.utils;

import static com.specture.core.constant.PropertyName.DOWNLOAD_METRIC_PARAMETER;
import static com.specture.core.constant.PropertyName.UPLOAD_METRIC_PARAMETER;

public class UnitForMeasurementParams {
    static String MBP = "Mbps";
    static String MS = "ms";

    static public String getUnitByAggregationName(String aggregationName) {
        switch (aggregationName) {
            case DOWNLOAD_METRIC_PARAMETER:
            case UPLOAD_METRIC_PARAMETER:
                return MBP;
        }
        return MS;
    }
}
