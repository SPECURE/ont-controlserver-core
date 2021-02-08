package com.specure.core.constant;

public interface URIConstants {
    String TEST_REQUEST_FOR_WEB_CLIENT = "/webTestRequest";
    String TEST_REQUEST_FOR_ADMIN = "/adminTestRequest";
    String MEASUREMENT_STATS_FOR_GENERAL_USER_PORTAL = "/measurementStats";
    String MEASUREMENT_RESULT = "/measurementResult";
    String MEASUREMENT_RESULT_BY_UUID = MEASUREMENT_RESULT + "/{uuid}";
    String MEASUREMENT_RESULT_QOS = "/measurementQosResult";
    String MEASUREMENT_QOS_REQUEST = "/qosTestRequest";
    String PROVIDERS = "/providers";
    String MEASUREMENT_SERVER = "/measurementServer";
    String MEASUREMENT_SERVER_ID = MEASUREMENT_SERVER + "/{measurementServerId}";
    String MEASUREMENT_SERVER_WEB = "/measurementServerWeb";
    String REQUEST_DATA_COLLECTOR = "/requestDataCollector";
    String RESULT = "/result";
    String TEST_RESULT = "/testResult";
    String TEST_RESULT_DETAIL = "/testResultDetail";
    String EXPORT_FULL = "/export/{fileExtension}";
    String EXPORT_MONTHLY = "/export/{year}-{month}/{fileExtension}";
}
