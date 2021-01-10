package com.specture.core.service.impl;

import com.blueconic.browscap.Capabilities;
import com.blueconic.browscap.UserAgentParser;
import com.specture.core.service.UserAgentExtractService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static com.specture.core.TestConstants.DEFAULT_STRING;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UserAgentExtractServiceImplTest {
    @MockBean
    private UserAgentParser userAgentParser;
    @Mock
    private Capabilities capabilities;

    private UserAgentExtractService userAgentExtractService;

    @Before
    public void setUp() {
        userAgentExtractService = new UserAgentExtractServiceImpl(userAgentParser);
    }

    @Test
    public void getBrowser_correctInvocation_returnBrowser() {
        when(userAgentParser.parse(anyString())).thenReturn(capabilities);
        when(capabilities.getBrowser()).thenReturn(DEFAULT_STRING);
        String result = userAgentExtractService.getBrowser("header");
        assertEquals(DEFAULT_STRING, result);
    }


}
