package com.specture.core.service;

import com.specture.core.response.DataCollectorResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface DataCollectorService {
    DataCollectorResponse extrudeData(HttpServletRequest request, Map<String, String> headers);
}
