package com.specure.core.service.impl;

import com.specure.core.response.DataCollectorResponse;
import com.specure.core.service.DataCollectorService;
import com.specure.core.utils.HeaderExtrudeUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class DataCollectorServiceImpl implements DataCollectorService {

    @Override
    public DataCollectorResponse extrudeData(HttpServletRequest request, Map<String, String> headers) {
        return DataCollectorResponse.builder()
                .agent(request.getHeader("User-Agent"))
                .headers(headers)
                .ip(HeaderExtrudeUtil.getIpFromNgNixHeader(headers))
                .port(request.getRemotePort())
                .build();
    }
}
