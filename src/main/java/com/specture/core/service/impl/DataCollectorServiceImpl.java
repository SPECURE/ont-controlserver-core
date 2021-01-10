package com.specture.core.service.impl;

import com.specture.core.response.DataCollectorResponse;
import com.specture.core.service.DataCollectorService;
import com.specture.core.utils.HeaderExtrudeUtil;
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
