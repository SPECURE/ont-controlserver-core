package com.specture.core.controller;

import com.specture.core.config.MeasurementServerConfig;
import com.specture.core.constant.MeasurementServerConstants;
import com.specture.core.constant.URIConstants;
import com.specture.core.model.Measurement;
import com.specture.core.request.*;
import com.specture.core.response.MeasurementHistoryResponse;
import com.specture.core.response.MeasurementRegistrationResponse;
import com.specture.core.response.MeasurementResultRMBTClientResponse;
import com.specture.core.response.measurement.qos.response.MeasurementQosParametersResponse;
import com.specture.core.service.BasicTestService;
import com.specture.core.service.MeasurementQosService;
import com.specture.core.service.MeasurementServerService;
import com.specture.core.service.MeasurementService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class MeasurementController {
    private final MeasurementService measurementService;
    @Qualifier("basicMeasurementQosService")
    private final MeasurementQosService basicMeasurementQosService;
    private final MeasurementServerConfig measurementServerConfig;
    @Qualifier("basicMeasurementServerService")
    private final MeasurementServerService basicMeasurementServerService;
    private final BasicTestService basicTestService;


    @ApiOperation("Return test servers and settings for web client measurements.")
    @PostMapping(URIConstants.TEST_REQUEST_FOR_WEB_CLIENT)
    public MeasurementRegistrationResponse registerMeasurementForWebClient(@Validated @RequestBody MeasurementRegistrationForWebClientRequest measurementRegistrationForWebClientRequest, @RequestHeader Map<String, String> headers) {
        var onlyMeasurementServerData = basicMeasurementServerService.getMeasurementServerForWebClient(measurementRegistrationForWebClientRequest);
        var response = measurementService.registerMeasurement(onlyMeasurementServerData, headers);
        response.setTestNumThreads(MeasurementServerConstants.TEST_NUM_THREADS_FOR_WEB);
        return response;
    }

    @ApiOperation("Return test servers chosen by admin and settings for admin client measurements.")
    @PostMapping(URIConstants.TEST_REQUEST_FOR_ADMIN)
    public MeasurementRegistrationResponse registerMeasurementForAdmin(@Validated @RequestBody MeasurementRegistrationForAdminRequest measurementRegistrationForAdminRequest, @RequestHeader Map<String, String> headers) {
        var dataWithServerChosenByAdminForMeasurement = basicMeasurementServerService.getMeasurementServerById(measurementRegistrationForAdminRequest);
        var response = measurementService.registerMeasurement(dataWithServerChosenByAdminForMeasurement, headers);
        response.setTestNumThreads(MeasurementServerConstants.TEST_NUM_THREADS_FOR_WEB);
        return response;
    }

    @ApiOperation("Save measurements results.")
    @PostMapping(URIConstants.MEASUREMENT_RESULT)
    public MeasurementResultRMBTClientResponse saveMeasurementResult(@Validated @RequestBody MeasurementRequest measurementRequest) {
        final String historyUrl = measurementServerConfig.getHost() + URIConstants.HISTORY;
        final Measurement measurement = measurementService.partialUpdateMeasurementFromProbeResult(measurementRequest);
        basicTestService.saveMeasurementToElastic(measurement);

        return MeasurementResultRMBTClientResponse.builder()
                .rmbtResultUrl(historyUrl)
                .error(Collections.emptyList())
                .build();
    }

    @ApiOperation("Save QoS measurements results.")
    @PostMapping(URIConstants.MEASUREMENT_RESULT_QOS)
    public MeasurementResultRMBTClientResponse saveMeasurementQOSResult(@Validated @RequestBody MeasurementQosRequest measurementQosRequest) {
        final String historyUrl = measurementServerConfig.getHost() + URIConstants.HISTORY;
        basicMeasurementQosService.saveMeasurementQos(measurementQosRequest);

        return MeasurementResultRMBTClientResponse.builder()
                .rmbtResultUrl(historyUrl)
                .error(Collections.emptyList())
                .build();
    }

    @ApiOperation("Provide parameters for QoS measurements.")
    @PostMapping(URIConstants.MEASUREMENT_QOS_REQUEST)
    public MeasurementQosParametersResponse provideMeasurementQoSParameters(@Validated @RequestBody MeasurementQosParametersRequest measurementQosParametersRequest) {
        return basicMeasurementQosService.getQosParameters(measurementQosParametersRequest);
    }

    @ApiOperation("Return comprehensive measurement result.")
    @GetMapping(URIConstants.MEASUREMENT_RESULT_BY_UUID)
    public MeasurementHistoryResponse getMeasurementResult(@PathVariable String uuid) {
        final MeasurementHistoryResponse measurementHistoryResponse = measurementService.getMeasurementDetailByUuid(uuid);
        measurementHistoryResponse.setMeasurementServerName(basicMeasurementServerService.getMeasurementServerById(measurementHistoryResponse.getMeasurementServerId()).getName());
        return measurementHistoryResponse;
    }

}
