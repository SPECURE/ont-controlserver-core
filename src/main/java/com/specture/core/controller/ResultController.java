package com.specture.core.controller;

import com.specture.core.response.measurement.result.MeasurementResultWebClientResponse;
import com.specture.core.request.MeasurementResultRequest;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.specture.core.constant.URIConstants.RESULT;

@RequiredArgsConstructor
@RestController
public class ResultController {

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Provide settings for web client.")
    @PostMapping(RESULT)
    public MeasurementResultWebClientResponse getMeasurementResult(@Validated @RequestBody MeasurementResultRequest measurementResultRequest) {
        return MeasurementResultWebClientResponse.builder().build();
    }
}
