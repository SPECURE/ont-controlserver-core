package com.specure.core.controller;

import com.specure.core.constant.URIConstants;
import com.specure.core.response.DataCollectorResponse;
import com.specure.core.service.DataCollectorService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class DataCollectorController {

    private final DataCollectorService dataCollectorService;

    @ApiOperation("Get data from request to client.")
    @GetMapping(URIConstants.REQUEST_DATA_COLLECTOR)
    public DataCollectorResponse getDataCollector(HttpServletRequest request, @RequestHeader Map<String, String> headers) {
        return dataCollectorService.extrudeData(request, headers);
    }
}
