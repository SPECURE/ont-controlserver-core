package com.specture.core.controller;

import com.specture.core.advice.ControllerErrorAdvice;
import com.specture.core.exception.MeasurementHistoryNotAccessibleException;
import com.specture.core.model.EnrichedPageWithAggregations;
import com.specture.core.response.BasicTestResponse;
import com.specture.core.service.BasicTestService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
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

import static com.specture.core.TestConstants.*;
import static com.specture.core.constant.ErrorMessage.MEASUREMENT_HISTORY_IS_NOT_AVAILABLE;
import static com.specture.core.constant.URIConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class BasicTestControllerTest {

    @MockBean
    private BasicTestService basicTestService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        BasicTestController basicTestController = new BasicTestController(basicTestService);
        mockMvc = MockMvcBuilders.standaloneSetup(basicTestController)
            .setControllerAdvice(new ControllerErrorAdvice())
            .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
            .setViewResolvers((viewName, locale) -> new MappingJackson2JsonView())
            .build();
    }

    @Test
    public void getBasicTests_WhenCalled_ExpectCorrectResponse() throws Exception {
        final BasicTestResponse basicTest = getDefaultBasicTestResponse();
        final PageImpl<BasicTestResponse> page = new PageImpl<>(Collections.singletonList(basicTest));
        final PageRequest pageRequest = PageRequest.of(DEFAULT_PAGE, DEFAULT_SIZE, Sort.Direction.DESC, DEFAULT_SORT_PROPERTY);

        EnrichedPageWithAggregations<BasicTestResponse> data = new EnrichedPageWithAggregations<>(page);
        when(basicTestService.getBasicTestsWithFilters(null, null, null, null, pageRequest))
            .thenReturn(data);

        mockMvc.perform(MockMvcRequestBuilders.get(BASIC_TEST)
            .contentType(MediaType.APPLICATION_JSON)
            .param("page", String.valueOf(DEFAULT_PAGE))
            .param("size", String.valueOf(DEFAULT_SIZE))
            .param("sort", DEFAULT_SORT)
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].openTestUuid").value(DEFAULT_OPEN_TEST_UUID))
            .andExpect(jsonPath("$.content[0].measurementDate").value(DEFAULT_MEASUREMENT_DATE))
            .andExpect(jsonPath("$.content[0].operator").value(DEFAULT_NETWORK_OPERATOR_NAME))
            .andExpect(jsonPath("$.content[0].download").value(DEFAULT_DOWNLOAD_SPEED))
            .andExpect(jsonPath("$.content[0].upload").value(DEFAULT_UPLOAD_SPEED))
            .andExpect(jsonPath("$.content[0].ping").value(DEFAULT_PING))
            .andExpect(jsonPath("$.content[0].jitter").value(DEFAULT_JITTER))
            .andExpect(jsonPath("$.content[0].packetLoss").value(DEFAULT_PACKET_LOSS));
    }

    @Test
    public void getBasicTests_WhenCalledMobile_ExpectCorrectResponse() throws Exception {
        final BasicTestResponse mobileTest = getDefaultBasicTestResponse();
        final PageImpl<BasicTestResponse> page = new PageImpl<>(Collections.singletonList(mobileTest));
        final PageRequest pageRequest = PageRequest.of(DEFAULT_PAGE, DEFAULT_SIZE, Sort.Direction.DESC, DEFAULT_SORT_PROPERTY);
        EnrichedPageWithAggregations<BasicTestResponse> data = new EnrichedPageWithAggregations<>(page);
        when(basicTestService.getMobileTestsWithFilters(null, null, null, null, pageRequest)).thenReturn(data);

        mockMvc.perform(MockMvcRequestBuilders.get(MOBILE_TEST)
            .contentType(MediaType.APPLICATION_JSON)
            .param("page", String.valueOf(DEFAULT_PAGE))
            .param("size", String.valueOf(DEFAULT_SIZE))
            .param("sort", DEFAULT_SORT)
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].openTestUuid").value(DEFAULT_OPEN_TEST_UUID))
            .andExpect(jsonPath("$.content[0].measurementDate").value(DEFAULT_MEASUREMENT_DATE))
            .andExpect(jsonPath("$.content[0].operator").value(DEFAULT_NETWORK_OPERATOR_NAME))
            .andExpect(jsonPath("$.content[0].download").value(DEFAULT_DOWNLOAD_SPEED))
            .andExpect(jsonPath("$.content[0].upload").value(DEFAULT_UPLOAD_SPEED))
            .andExpect(jsonPath("$.content[0].ping").value(DEFAULT_PING))
            .andExpect(jsonPath("$.content[0].signal").value(DEFAULT_SIGNAL_STRENGTH))
            .andExpect(jsonPath("$.content[0].jitter").value(DEFAULT_JITTER))
            .andExpect(jsonPath("$.content[0].networkType").value(DEFAULT_NETWORK_TYPE.toString()))
            .andExpect(jsonPath("$.content[0].packetLoss").value(DEFAULT_PACKET_LOSS));
    }

    @Test
    public void getBasicTest_WhenCallWeb_ExpectCorrectResponse() throws Exception {
        final BasicTestResponse webTestResponse = getDefaultBasicTestResponse();
        final PageImpl<BasicTestResponse> pageOfBasicTestResponse = new PageImpl<>(Collections.singletonList(webTestResponse));
        final PageRequest pageRequest = PageRequest.of(DEFAULT_PAGE, DEFAULT_SIZE, Sort.Direction.DESC, DEFAULT_SORT_PROPERTY);
        EnrichedPageWithAggregations<BasicTestResponse> data = new EnrichedPageWithAggregations<>(pageOfBasicTestResponse);
        when(basicTestService.getWebTests(DEFAULT_DATE_TIME_FROM, DEFAULT_DATE_TIME_TO, pageRequest)).thenReturn(data);

        mockMvc.perform(MockMvcRequestBuilders
            .get(WEB_TEST)
            .contentType(MediaType.APPLICATION_JSON)
            .param("dateFrom", DEFAULT_DATE_TIME_FROM_STRING)
            .param("dateTo", DEFAULT_DATE_TIME_TO_STRING)
            .param("page", String.valueOf(DEFAULT_PAGE))
            .param("size", String.valueOf(DEFAULT_SIZE))
            .param("sort", DEFAULT_SORT)
        ).andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].openTestUuid").value(DEFAULT_OPEN_TEST_UUID))
            .andExpect(jsonPath("$.content[0].measurementDate").value(DEFAULT_MEASUREMENT_DATE))
            .andExpect(jsonPath("$.content[0].operator").value(DEFAULT_NETWORK_OPERATOR_NAME))
            .andExpect(jsonPath("$.content[0].download").value(DEFAULT_DOWNLOAD_SPEED))
            .andExpect(jsonPath("$.content[0].upload").value(DEFAULT_UPLOAD_SPEED))
            .andExpect(jsonPath("$.content[0].ping").value(DEFAULT_PING))
            .andExpect(jsonPath("$.content[0].signal").value(DEFAULT_SIGNAL_STRENGTH))
            .andExpect(jsonPath("$.content[0].jitter").value(DEFAULT_JITTER))
            .andExpect(jsonPath("$.content[0].networkType").value(DEFAULT_NETWORK_TYPE.toString()))
            .andExpect(jsonPath("$.content[0].packetLoss").value(DEFAULT_PACKET_LOSS));

        verify(basicTestService).getWebTests(DEFAULT_DATE_TIME_FROM, DEFAULT_DATE_TIME_TO, pageRequest);
    }

    @Test
    public void getBasicTest_WhenCallGetBasicTestsByClientUuid_ExpectCorrectResponse() throws Exception {
        final BasicTestResponse webTestResponse = getDefaultBasicTestResponse();
        final PageImpl<BasicTestResponse> pageOfBasicTestResponse = new PageImpl<>(Collections.singletonList(webTestResponse));
        final PageRequest pageRequest = PageRequest.of(DEFAULT_PAGE, DEFAULT_SIZE, Sort.Direction.DESC, DEFAULT_SORT_PROPERTY);

        when(basicTestService.getBasicTestsByUuid(pageRequest, DEFAULT_UUID)).thenReturn(pageOfBasicTestResponse);

        mockMvc.perform(MockMvcRequestBuilders
            .get(BASIC_TEST_BY_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .param("uuid", DEFAULT_UUID)
            .param("page", String.valueOf(DEFAULT_PAGE))
            .param("size", String.valueOf(DEFAULT_SIZE))
            .param("sort", DEFAULT_SORT)
        ).andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].openTestUuid").value(DEFAULT_OPEN_TEST_UUID))
            .andExpect(jsonPath("$.content[0].measurementDate").value(DEFAULT_MEASUREMENT_DATE))
            .andExpect(jsonPath("$.content[0].operator").value(DEFAULT_NETWORK_OPERATOR_NAME))
            .andExpect(jsonPath("$.content[0].download").value(DEFAULT_DOWNLOAD_SPEED))
            .andExpect(jsonPath("$.content[0].upload").value(DEFAULT_UPLOAD_SPEED))
            .andExpect(jsonPath("$.content[0].ping").value(DEFAULT_PING))
            .andExpect(jsonPath("$.content[0].signal").value(DEFAULT_SIGNAL_STRENGTH))
            .andExpect(jsonPath("$.content[0].jitter").value(DEFAULT_JITTER))
            .andExpect(jsonPath("$.content[0].networkType").value(DEFAULT_NETWORK_TYPE.toString()))
            .andExpect(jsonPath("$.content[0].packetLoss").value(DEFAULT_PACKET_LOSS));
        verify(basicTestService).getBasicTestsByUuid(pageRequest, DEFAULT_UUID);
    }

    @Test
    public void getBasicTestsByClientUuid_WhenExceptionInController_ExpectBadRequest() throws Exception {
        doThrow(new MeasurementHistoryNotAccessibleException())
            .when(basicTestService)
            .getBasicTestsByUuid(any(), any());

        mockMvc.perform(MockMvcRequestBuilders
            .get(BASIC_TEST_BY_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .param("page", String.valueOf(DEFAULT_PAGE))
            .param("size", String.valueOf(DEFAULT_SIZE))
            .param("sort", DEFAULT_SORT)
        )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message")
                .value(MEASUREMENT_HISTORY_IS_NOT_AVAILABLE)
            );
    }

    private BasicTestResponse getDefaultBasicTestResponse() {
        return BasicTestResponse.builder()
            .openTestUuid(DEFAULT_OPEN_TEST_UUID)
            .measurementDate(DEFAULT_MEASUREMENT_DATE)
            .operator(DEFAULT_NETWORK_OPERATOR_NAME)
            .download(DEFAULT_DOWNLOAD_SPEED)
            .upload(DEFAULT_UPLOAD_SPEED)
            .ping(DEFAULT_PING)
            .signal(DEFAULT_SIGNAL_STRENGTH)
            .networkType(DEFAULT_NETWORK_TYPE.toString())
            .jitter(DEFAULT_JITTER)
            .packetLoss(DEFAULT_PACKET_LOSS)
            .build();
    }

}
