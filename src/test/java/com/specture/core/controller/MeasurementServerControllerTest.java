package com.specture.core.controller;

import com.specture.core.TestUtils;
import com.specture.core.advice.ControllerErrorAdvice;
import com.specture.core.exception.MeasurementServerNotFoundException;
import com.specture.core.exception.ProviderNotFoundByIdException;
import com.specture.core.request.ClientLocationRequest;
import com.specture.core.request.MeasurementServerRequest;
import com.specture.core.response.MeasurementServerResponse;
import com.specture.core.response.NearestMeasurementServersResponse;
import com.specture.core.service.MeasurementServerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;
import java.util.List;

import static com.specture.core.TestConstants.*;
import static com.specture.core.constant.URIConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class MeasurementServerControllerTest {

    @MockBean
    private MeasurementServerService measurementServerService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MeasurementServerController measurementServerController = new MeasurementServerController(measurementServerService);
        mockMvc = MockMvcBuilders
            .standaloneSetup(measurementServerController)
            .setControllerAdvice(new ControllerErrorAdvice())
            .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
            .setViewResolvers((viewName, locale) -> new MappingJackson2JsonView())
            .build();
    }

    @Test
    public void measurementServerController_WhenCallGetAll_ExpectCorrectResponse() throws Exception {
        List<MeasurementServerResponse> response = Collections.emptyList();
        when(measurementServerService.getMeasurementServers(any())).thenReturn(response);
        mockMvc
            .perform(MockMvcRequestBuilders.get(MEASUREMENT_SERVER))
            .andExpect(status().isOk());
    }

    @Test
    public void measurementServerController_WhenCallGetAll_ExpectRunService() throws Exception {
        List<MeasurementServerResponse> response = Collections.emptyList();
        PageRequest pageRequest = PageRequest.of(DEFAULT_PAGE, DEFAULT_SIZE, Sort.Direction.DESC, DEFAULT_SORT_PROPERTY);

        when(measurementServerService.getMeasurementServers(null)).thenReturn(response);
        mockMvc
            .perform(MockMvcRequestBuilders.get(MEASUREMENT_SERVER)
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", String.valueOf(DEFAULT_PAGE))
                .param("size", String.valueOf(DEFAULT_SIZE))
                .param("sort", DEFAULT_SORT)
            )
            .andExpect(status().isOk());

        verify(measurementServerService).getMeasurementServers(null);
    }

    @Test
    public void measurementServerController_WhenCallCreateNew_ExpectCorrectResponse() throws Exception {
        MeasurementServerRequest measurementServerRequest = getDefaultMeasurementServerRequest();
        mockMvc
            .perform(MockMvcRequestBuilders.post(MEASUREMENT_SERVER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.convertObjectToJsonBytes(measurementServerRequest))
            ).andExpect(status().isCreated());
    }

    @Test
    public void measurementServerController_WhenCallCreateNewWithNotExistProvider_ExpectBadRequest() throws Exception {
        MeasurementServerRequest measurementServerRequest = getDefaultMeasurementServerRequest();
        doThrow(new ProviderNotFoundByIdException(DEFAULT_ID))
            .when(measurementServerService)
            .createMeasurementServer(eq(measurementServerRequest));
        mockMvc
            .perform(MockMvcRequestBuilders.post(MEASUREMENT_SERVER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.convertObjectToJsonBytes(measurementServerRequest))
            ).andExpect(status().isBadRequest());
    }

    @Test
    public void measurementServerController_WhenCallUpdate_ExpectCorrectResponse() throws Exception {
        MeasurementServerRequest measurementServerRequest = getDefaultMeasurementServerRequest();
        mockMvc
            .perform(MockMvcRequestBuilders.put(MEASUREMENT_SERVER_ID, DEFAULT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.convertObjectToJsonBytes(measurementServerRequest))
            ).andExpect(status().isOk());
    }

    @Test
    public void getMeasurementServersForWebClient_WhenCallUpdate_ExpectCorrectResponse() throws Exception {
        ClientLocationRequest clientLocationRequest = ClientLocationRequest.builder().client(DEFAULT_CLIENT).build();
        NearestMeasurementServersResponse nearestMeasurementServersResponse = NearestMeasurementServersResponse.builder().build();

        when(measurementServerService.getNearestServers(clientLocationRequest)).thenReturn(nearestMeasurementServersResponse);

        mockMvc
            .perform(MockMvcRequestBuilders.post(MEASUREMENT_SERVER_WEB)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.convertObjectToJsonBytes(clientLocationRequest))
            ).andExpect(status().isOk());
    }

    @Test
    public void measurementServerController_WhenCallUpdateWithWrongId_ExpectBadRequest() throws Exception {
        MeasurementServerRequest measurementServerRequest = getDefaultMeasurementServerRequest();
        doThrow(new MeasurementServerNotFoundException(DEFAULT_ID))
            .when(measurementServerService)
            .updateMeasurementServer(DEFAULT_ID, measurementServerRequest);
        mockMvc
            .perform(MockMvcRequestBuilders.put(MEASUREMENT_SERVER_ID, DEFAULT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.convertObjectToJsonBytes(measurementServerRequest))
            ).andExpect(status().isBadRequest());
    }


    private MeasurementServerRequest getDefaultMeasurementServerRequest() {
        return MeasurementServerRequest.builder()
            .name(DEFAULT_MEASUREMENT_SERVER_NAME)
            .webAddress(DEFAULT_SITE_ADDRESS)
            .providerId(DEFAULT_ID)
            .build();
    }

}
