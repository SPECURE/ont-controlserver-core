package com.specture.core.controller;

import com.specture.core.constant.URIConstants;
import com.specture.core.request.MeasurementResultRequest;
import com.specture.core.response.measurement.result.Detail.MeasurementDetailResultResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class MeasurementResultDetailController {
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Return measurement result detail for web client.")
    @PostMapping(URIConstants.TEST_RESULT_DETAIL)
    public MeasurementDetailResultResponse measurementResultDetail(@Validated @RequestBody MeasurementResultRequest measurementResultRequest) {
        return MeasurementDetailResultResponse.builder().build();
    }
}
