package com.specure.core.service.impl;

import com.specure.core.repository.ClientRepository;
import com.specure.core.request.SettingRequest;
import com.specure.core.response.settings.SettingsResponse;
import com.specure.core.service.SettingsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class SettingsServiceImplTest {

    @MockBean
    private ClientRepository clientRepository;
    @Mock
    private SettingRequest settingRequest;

    private SettingsService settingsService;

    @Before
    public void setUp() {
        settingsService = new SettingsServiceImpl(clientRepository);
    }

    @Test
    public void getSettingsByRequest_correctRequest_settingsNotNull() {
        SettingsResponse settingsResponse = settingsService.getSettingsByRequest(settingRequest);
        verify(clientRepository).save(any());
        assertNotNull(settingsResponse);
    }

}
