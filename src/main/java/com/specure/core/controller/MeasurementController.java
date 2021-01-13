package com.specure.core.controller;

import com.specure.core.config.MeasurementServerConfig;
import com.specure.core.constant.MeasurementServerConstants;
import com.specure.core.constant.URIConstants;
import com.specure.core.request.MeasurementQosParametersRequest;
import com.specure.core.request.MeasurementRegistrationForAdminRequest;
import com.specure.core.request.MeasurementRegistrationForWebClientRequest;
import com.specure.core.response.MeasurementHistoryResponse;
import com.specure.core.response.MeasurementRegistrationResponse;
import com.specure.core.response.measurement.qos.response.MeasurementQosParametersResponse;
import com.specure.core.service.MeasurementQosService;
import com.specure.core.service.MeasurementServerService;
import com.specure.core.service.MeasurementService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@Slf4j
public class MeasurementController {
    private final MeasurementService measurementService;
    @Qualifier("basicMeasurementQosService")
    private final MeasurementQosService basicMeasurementQosService;
    private final MeasurementServerConfig measurementServerConfig;
    @Qualifier("basicMeasurementServerService")
    private final MeasurementServerService basicMeasurementServerService;


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
