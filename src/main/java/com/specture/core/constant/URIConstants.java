package com.specture.core.constant;

public interface URIConstants {
    String BASIC_TEST = "/reports/basic";
    String MOBILE_TEST = "/reports/mobile";
    String WEB_TEST = "/reports/web";
    String BASIC_QOS_TEST_FIXED = "/reports/basic-qos";
    String BASIC_QOS_TEST_MOBILE = "/reports/mobile-qos";
    String BASIC_TEST_BY_UUID = "/reports/basic/history";
    String BASIC_QOS_TEST_BY_UUID = "/reports/basic-qos/history";
    String TEST_REQUEST = "/testRequest";
    String TEST_REQUEST_FOR_WEB_CLIENT = "/webTestRequest";
    String TEST_REQUEST_FOR_ADMIN = "/adminTestRequest";
    String MEASUREMENT_STATS_FOR_GENERAL_USER_PORTAL = "/measurementStats";
    String MEASUREMENT_RESULT = "/measurementResult";
    String MEASUREMENT_RESULT_BY_UUID = MEASUREMENT_RESULT + "/{uuid}";
    String MEASUREMENT_RESULT_QOS = "/measurementQosResult";
    String HISTORY = "/en/history/";
    String MEASUREMENT_QOS_REQUEST = "/qosTestRequest";
    String PROVIDERS = "/providers";
    String MEASUREMENT_SERVER = "/measurementServer";
    String MEASUREMENT_SERVER_ID = MEASUREMENT_SERVER + "/{measurementServerId}";
    String MEASUREMENT_SERVER_WEB = "/measurementServerWeb";
    String SETTINGS = "/settings";
    String REQUEST_DATA_COLLECTOR = "/requestDataCollector";
    String RESULT = "/result";
    String TEST_RESULT = "/testResult";
    String TEST_RESULT_DETAIL = "/testResultDetail";
    String EXPORT_FULL = "/export/{fileExtension}";
    String EXPORT_MONTHLY = "/export/{year}-{month}/{fileExtension}";
}
