package com.specure.core.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.specure.core.request.measurement.request.GeoLocationRequest;
import com.specure.core.request.measurement.request.PingRequest;
import com.specure.core.request.measurement.request.SpeedDetailRequest;
import com.specure.core.constant.ErrorMessage;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Builder
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MeasurementRequest {

    private String clientLanguage;
    private String clientName;
    private String clientVersion;

    @NotNull(message = ErrorMessage.CLIENT_UUID_REQUIRED)
    private String clientUuid;

    @JsonProperty("geoLocations")
    private List<GeoLocationRequest> geoLocations;

    private String model;
    private String networkType;
    @JsonProperty("platform")
    @JsonAlias({"plattform", "platform"})
    private String platform;
    private String product;

    private ArrayList<PingRequest> pings;

    private Long testBytesDownload;
    private Long testBytesUpload;

    private Long testNsecDownload;
    private Long testNsecUpload;
    private Integer testNumThreads;
    private Integer numThreadsUl;
    private Long testPingShortest;
    private Integer testSpeedDownload;
    private Integer testSpeedUpload;

    @NotNull(message = ErrorMessage.TEST_TOKEN_REQUIRED)
    private String testToken;
    private String testUUuid;
    private Long time;
    private String timezone;
    private String type;
    private String versionCode;
    private List<SpeedDetailRequest> speedDetail;
    private Integer userServerSelection;
    private String loopUuid;

    // QOS ?
    private String device;
    private String tag;
    private String telephonyNetworkCountry;
    private String telephonyNetworkSimCountry;
    private Integer testPortRemote;
    private Long testTotalBytesDownload;
    private Long testTotalBytesUpload;
    private String testEncryption;
    private String testIpLocal;
    private String testIpServer;
    private Integer testIfBytesDownload;
    private Integer testIfBytesUpload;
    private Integer testdlIfBytesDownload;
    private Integer testdlIfBytesUpload;
    private Integer testulIfBytesDownload;
    private Integer testulIfBytesUpload;
    private Map<String, String> jpl;
    private List<Map<String, Integer>> signals;
}
