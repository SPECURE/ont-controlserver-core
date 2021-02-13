package com.specure.core.controller;

import com.specure.core.advice.ControllerErrorAdvice;
import com.specure.core.constant.URIConstants;
import com.specure.core.service.OpenDataService;
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

import static com.specure.core.TestConstants.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class OpenDataControllerTest {

    @MockBean
    OpenDataService openDataService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        OpenDataController providerController = new OpenDataController(openDataService);
        mockMvc = MockMvcBuilders.standaloneSetup(providerController)
                .setControllerAdvice(new ControllerErrorAdvice())
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((viewName, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    public void getMonthlyExportFile_whenCall_ExpectCorrectResponse() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(
                URIConstants.EXPORT_MONTHLY,
                DEFAULT_OPEN_DATA_YEAR, DEFAULT_OPEN_DATA_MONTH, DEFAULT_OPEN_DATA_FILE_EXTENSION
                )
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("digitalSeparator", "COMMA")
        ).andExpect(status().isOk());
    }

    @Test
    public void getFullExportFile_whenCall_ExpectCorrectResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(URIConstants.EXPORT_FULL, DEFAULT_OPEN_DATA_FILE_EXTENSION)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("digitalSeparator", "DOT")
        ).andExpect(status().isOk());
    }

    @Test
    public void getFullExportFile_whenCallWithDefaultDigitalSeparator_ExpectCorrectResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(URIConstants.EXPORT_FULL, DEFAULT_OPEN_DATA_FILE_EXTENSION)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isOk());
    }
}
