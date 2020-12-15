package com.specture.core.constant;

import static com.specture.core.constant.URIConstants.*;

public interface MeasurementServerConstants {
    String RESULT_URL =  "%s" +  MEASUREMENT_RESULT;
    String RESULT_QOS_URL = "%s" + MEASUREMENT_RESULT_QOS;
    Integer TEST_NUM_THREADS = 20;
    Integer TEST_NUM_THREADS_FOR_WEB = 3;
    String TEST_NUM_PINGS = "10";
    String JIRA_REPORT_HEADER = "\"%s\" installed a new Open Nettest measurement server with the following details:";
    String JIRA_SUMMARY = "Request for Measurement server registration via REST";
}
