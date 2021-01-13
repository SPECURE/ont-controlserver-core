package com.specure.core.controller;

import com.specure.core.TestUtils;
import com.specure.core.advice.ControllerErrorAdvice;
import com.specure.core.request.SettingRequest;
import com.specure.core.response.settings.SettingsResponse;
import com.specure.core.service.SettingsService;
import com.specure.core.constant.URIConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class SettingsControllerTest {

    @MockBean
    SettingsService settingsService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        SettingsController settingsController = new SettingsController(settingsService);
        mockMvc = MockMvcBuilders.standaloneSetup(settingsController)
            .setControllerAdvice(new ControllerErrorAdvice())
            .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
            .setViewResolvers((viewName, locale) -> new MappingJackson2JsonView())
            .build();
    }

    @Test
    public void getSettingsForWevClient_WhenCalled_ExpectCorrectResponse() throws Exception {
        SettingRequest settingRequest = SettingRequest.builder().build();
        SettingsResponse settingsResponse = SettingsResponse.builder().build();
        when(settingsService.getSettingsByRequest(settingRequest)).thenReturn(settingsResponse);
        mockMvc.perform(MockMvcRequestBuilders
            .post(URIConstants.SETTINGS)
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtils.convertObjectToJsonBytes(settingRequest))
        ).andExpect(status().isOk());
    }
}
