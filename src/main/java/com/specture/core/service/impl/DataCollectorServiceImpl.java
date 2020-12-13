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
//                .category("PERSONAL_COMPUTER")
//                .countryGeoIp("UA")
//                .family("CHROME")
                .headers(headers)
                .ip(HeaderExtrudeUtil.getIpFromNgNixHeader(headers))
//                .languages(List.of("ru-RU", "ru", "en-US", "en", "uk"))
//                .os("OS X")
                .port(request.getRemotePort())
//                .product("Chrome")
//                .version("83")
//                .version_minor("0")
                .build();
    }
}
