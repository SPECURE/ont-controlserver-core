package com.specture.core.service.impl;

import com.specture.core.response.DataCollectorResponse;
import com.specture.core.service.DataCollectorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.specture.core.TestConstants.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class DataCollectorServiceImplTest {
    @Mock
    private HttpServletRequest httpRequest;

    private Map<String, String> headers = new HashMap<>();

    private DataCollectorService dataCollectorService;

    @Before
    public void setUp() {
        dataCollectorService = new DataCollectorServiceImpl();
    }

    @Test
    public void extrudeData_correctRequest_expectExtrudedXRealIp() {
        when(httpRequest.getHeader("User-Agent")).thenReturn(DEFAULT_USER_AGENT);
        headers.put("x-real-ip", DEFAULT_X_REAL_IP);
        when(httpRequest.getRemotePort()).thenReturn(DEFAULT_REMOTE_PORT);
        DataCollectorResponse response = dataCollectorService.extrudeData(httpRequest, headers);
        assertEquals(headers, response.getHeaders());
        assertEquals(DEFAULT_X_REAL_IP, response.getIp());
        assertEquals(DEFAULT_REMOTE_PORT, response.getPort());
    }

    @Test
    public void extrudeData_correctRequest_expectExtrudedXForwarded() {
        when(httpRequest.getHeader("User-Agent")).thenReturn(DEFAULT_USER_AGENT);
        headers.put("x-forwarded-for", DEFAULT_X_FORWARDED);
        when(httpRequest.getRemotePort()).thenReturn(DEFAULT_REMOTE_PORT);
        DataCollectorResponse response = dataCollectorService.extrudeData(httpRequest, headers);
        assertEquals(headers, response.getHeaders());
        assertEquals(DEFAULT_X_FORWARDED, response.getIp());
        assertEquals(DEFAULT_REMOTE_PORT, response.getPort());
    }

    @Test
    public void extrudeData_correctRequestUnknownIp_expectExtrudedUnknownIp() {
        when(httpRequest.getHeader("User-Agent")).thenReturn(DEFAULT_USER_AGENT);
        when(httpRequest.getRemotePort()).thenReturn(DEFAULT_REMOTE_PORT);
        DataCollectorResponse response = dataCollectorService.extrudeData(httpRequest, headers);
        assertEquals(headers, response.getHeaders());
        assertEquals("ip unknown", response.getIp());
        assertEquals(DEFAULT_REMOTE_PORT, response.getPort());
    }

}
