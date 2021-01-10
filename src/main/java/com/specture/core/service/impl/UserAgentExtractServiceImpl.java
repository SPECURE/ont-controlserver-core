package com.specture.core.service.impl;

import com.blueconic.browscap.Capabilities;
import com.blueconic.browscap.UserAgentParser;
import com.specture.core.service.UserAgentExtractService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserAgentExtractServiceImpl implements UserAgentExtractService {

    private final UserAgentParser userAgentParser;

    @Override
    public String getBrowser(String userAgentHeader) {
        final Capabilities capabilities = userAgentParser.parse(userAgentHeader);
        return capabilities.getBrowser();
    }
}
