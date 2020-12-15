package com.specture.core.service.impl;

import com.blueconic.browscap.*;
import com.specture.core.service.UserAgentExtractService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;

@Service
public class UserAgentExtractServiceImpl implements UserAgentExtractService {
    final UserAgentParser parser;

    public UserAgentExtractServiceImpl() throws IOException, ParseException {
        parser = new UserAgentService().loadParser(Collections.singletonList(BrowsCapField.BROWSER));
    }

    @Override
    public String getBrowser(String userAgentHeader) {
        final Capabilities capabilities = parser.parse(userAgentHeader);
        return capabilities.getBrowser();
    }
}
