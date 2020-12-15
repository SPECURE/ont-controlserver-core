package com.specture.core.controller;

import com.specture.core.annotation.ApiPageable;
import com.specture.core.constant.URIConstants;
import com.specture.core.request.ClientLocationRequest;
import com.specture.core.request.MeasurementServerRequest;
import com.specture.core.response.MeasurementServerResponse;
import com.specture.core.response.NearestMeasurementServersResponse;
import com.specture.core.service.MeasurementServerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MeasurementServerController {
    @Qualifier("basicMeasurementServerService")
    private final MeasurementServerService basicMeasurementServerService;

    @ApiPageable
    @ApiOperation("Get list of measurement servers.")
    @GetMapping(URIConstants.MEASUREMENT_SERVER)
    public List<MeasurementServerResponse> getMeasurementServers(
            @ApiParam(value = "providerId") Long providerId
    ) {
        return basicMeasurementServerService.getMeasurementServers(providerId);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get list of measurement servers for web client")
    @PostMapping(URIConstants.MEASUREMENT_SERVER_WEB)
    public NearestMeasurementServersResponse getMeasurementServersForWebClient(@Validated @RequestBody ClientLocationRequest clientLocationRequest) {
        return basicMeasurementServerService.getNearestServers(clientLocationRequest);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Create new measurement server.")
    @PostMapping(URIConstants.MEASUREMENT_SERVER)
    public void createMeasurementServer(@Validated @RequestBody MeasurementServerRequest measurementServerRequest) {
        basicMeasurementServerService.createMeasurementServer(measurementServerRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Update existed measurement server")
    @PutMapping(URIConstants.MEASUREMENT_SERVER_ID)
    public void updateMeasurementServer(@PathVariable Long measurementServerId, @Validated @RequestBody MeasurementServerRequest measurementServerRequest) {
        basicMeasurementServerService.updateMeasurementServer(measurementServerId, measurementServerRequest);
    }

}
