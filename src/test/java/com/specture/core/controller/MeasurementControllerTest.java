package com.specture.core.controller;

import com.specture.core.TestUtils;
import com.specture.core.advice.ControllerErrorAdvice;
import com.specture.core.config.MeasurementServerConfig;
import com.specture.core.constant.ErrorMessage;
import com.specture.core.exception.ProbePortNotFoundException;
import com.specture.core.model.MeasurementServer;
import com.specture.core.model.internal.DataForMeasurementRegistration;
import com.specture.core.request.*;
import com.specture.core.response.MeasurementHistoryResponse;
import com.specture.core.response.MeasurementRegistrationResponse;
import com.specture.core.response.MeasurementStatsForGeneralUserPortalResponse;
import com.specture.core.service.BasicTestService;
import com.specture.core.service.MeasurementQosService;
import com.specture.core.service.MeasurementServerService;
import com.specture.core.service.MeasurementService;
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

import java.time.DayOfWeek;

import static com.specture.core.TestConstants.*;
import static com.specture.core.constant.ErrorMessage.*;
import static com.specture.core.constant.URIConstants.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class MeasurementControllerTest {

    @MockBean
    private MeasurementService measurementService;

    @MockBean
    private MeasurementServerService measurementServerService;

    @MockBean
    private MeasurementQosService measurementQosService;

    @MockBean
    private MeasurementServerConfig measurementServerConfig;

    @MockBean
    private BasicTestService basicTestService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MeasurementController measurementResultController = new MeasurementController(measurementService, measurementQosService, measurementServerConfig, measurementServerService, basicTestService);
        mockMvc = MockMvcBuilders.standaloneSetup(measurementResultController)
            .setControllerAdvice(new ControllerErrorAdvice())
            .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
            .setViewResolvers((viewName, locale) -> new MappingJackson2JsonView())
            .build();
    }

    @Test
    public void measurementRequest_WhenCalled_ExpectCorrectResponse() throws Exception {
        final var measurementServerRequest = getDefaultMeasurementServerRequest();
        final var measurementServer = MeasurementServer.builder().build();
        final DataForMeasurementRegistration dataForMeasurementRegistration = DataForMeasurementRegistration.builder()
            .measurementServer(measurementServer)
            .deviceOrProbeId(DEFAULT_PROBE_ID)
            .port(DEFAULT_PORT)
            .build();

        when(measurementServerService.getDataFromProbeMeasurementRegistrationRequest(measurementServerRequest))
            .thenReturn(dataForMeasurementRegistration);
        when(measurementService.registerMeasurement(any(), any()))
            .thenReturn(getDefaultResponse());

        mockMvc.perform(MockMvcRequestBuilders
            .post(TEST_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtils.convertObjectToJsonBytes(measurementServerRequest))
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.test_id").isNotEmpty());
    }

    @Test
    public void registerMeasurementForWebClient_WhenCalled_ExpectCorrectResponse() throws Exception {
        final var requestFromWebClient = MeasurementRegistrationForWebClientRequest.builder().client(DEFAULT_CLIENT).build();
        final var measurementServer = MeasurementServer.builder().build();
        final DataForMeasurementRegistration dataForMeasurementRegistration = DataForMeasurementRegistration.builder()
            .measurementServer(measurementServer)
            .build();

        when(measurementServerService.getMeasurementServerForWebClient(requestFromWebClient))
            .thenReturn(dataForMeasurementRegistration);
        when(measurementService.registerMeasurement(any(), any()))
            .thenReturn(getDefaultResponse());

        mockMvc.perform(MockMvcRequestBuilders
            .post(TEST_REQUEST_FOR_WEB_CLIENT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtils.convertObjectToJsonBytes(requestFromWebClient))
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.test_id").isNotEmpty());
    }

    @Test
    public void saveTestResult_WhenCalled_ExpectCorrectResponse() throws Exception {
        var measurementRequest = MeasurementRequest.builder()
            .clientUuid(DEFAULT_UUID)
            .testToken(DEFAULT_TOKEN)
            .build();

        mockMvc.perform(MockMvcRequestBuilders.post(MEASUREMENT_RESULT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtils.convertObjectToJsonBytes(measurementRequest))
        ).andExpect(status().isOk());

        verify(measurementService).partialUpdateMeasurementFromProbeResult(eq(measurementRequest));
    }

    @Test
    public void saveTestResult_WhenCalledWithoutTestToken_ExpectBadRequest() throws Exception {
        var measurementRequest = MeasurementRequest.builder()
            .clientUuid(DEFAULT_UUID)
            .build();

        mockMvc.perform(MockMvcRequestBuilders.post(MEASUREMENT_RESULT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtils.convertObjectToJsonBytes(measurementRequest))
        )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value(TEST_TOKEN_REQUIRED));
        verify(measurementService, never()).partialUpdateMeasurementFromProbeResult(any());
    }

    @Test
    public void saveMeasurementQoS_WhenCalled_ExpectCorrectResponse() throws Exception {
        MeasurementQosRequest measurementQosRequest = MeasurementQosRequest.builder()
            .testToken(DEFAULT_TOKEN)
            .build();
        mockMvc.perform(MockMvcRequestBuilders.post(MEASUREMENT_RESULT_QOS)
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtils.convertObjectToJsonBytes(measurementQosRequest))
        ).andExpect(status().isOk());

        verify(measurementQosService).saveMeasurementQos(measurementQosRequest);
    }

    @Test
    public void saveMeasurementQoS_WhenCalledWithoutTestToken_ExpectBadRequest() throws Exception {
        MeasurementQosRequest measurementQosRequestWithoutTestToken = MeasurementQosRequest.builder().build();

        mockMvc.perform(MockMvcRequestBuilders.post(MEASUREMENT_RESULT_QOS)
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtils.convertObjectToJsonBytes(measurementQosRequestWithoutTestToken))
        )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value(TEST_TOKEN_REQUIRED));

        verify(measurementQosService, never()).saveMeasurementQos(any());
    }

    @Test
    public void measurementQoSRequest_WhenCalled_ExpectCorrectResponse() throws Exception {
        MeasurementQosRequest measurementQosRequest = MeasurementQosRequest.builder()
            .testToken(DEFAULT_TOKEN)
            .build();

        mockMvc.perform(MockMvcRequestBuilders.post(MEASUREMENT_QOS_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtils.convertObjectToJsonBytes(measurementQosRequest))
        ).andExpect(status().isOk());
    }

    @Test
    public void saveMeasurementQoS_WhenNoVoipSection_ExpectBadRequest() {
        String jsonRequest = "{" +
            "    \"test_token\": \"afbf5a66-2754-4923-ba33-ed4ff88521f9_1589289590_qQJyNQslKzqXxFKFdLozQJCLfrM=\",\n" +
            "    \"client_uuid\": \"540607f2-2a43-4019-af89-fa6d42b9a14f\",\n" +
            "    \"time\": 1589289648171,\n" +
            "    \"client_version\": \"0.3\",\n" +
            "    \"qos_result\": []" +
            "}";
        try {
            mockMvc.perform(MockMvcRequestBuilders.post(MEASUREMENT_RESULT_QOS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
            );

        } catch (Exception e) {
            System.out.println("e.getMessage() = " + e.getMessage());
            assert (e.getMessage()).equals("The measurement result has wrong format: QoS measurement result has to consist VOIP section.");
        }
    }

    @Test
    public void registerMeasurementForAdmin_WhenCalled_ExpectCorrectResponse() throws Exception {

        final MeasurementRegistrationForAdminRequest measurementRegistrationForAdminRequest = MeasurementRegistrationForAdminRequest.builder()
            .measurementServerId(DEFAULT_MEASUREMENT_SERVER_ID)
            .client(DEFAULT_CLIENT)
            .uuid(DEFAULT_UUID)
            .build();

        final DataForMeasurementRegistration dataForMeasurementRegistration = DataForMeasurementRegistration.builder()
            .measurementServer(MeasurementServer.builder().id(DEFAULT_MEASUREMENT_SERVER_ID).build())
            .deviceOrProbeId(DEFAULT_PROBE_ID)
            .port(DEFAULT_PORT)
            .build();

        final MeasurementRegistrationResponse measurementRegistrationResponse = getDefaultResponse();

        when(measurementServerService.getMeasurementServerById(measurementRegistrationForAdminRequest))
            .thenReturn(dataForMeasurementRegistration);
        when(measurementService.registerMeasurement(eq(dataForMeasurementRegistration), any()))
            .thenReturn(measurementRegistrationResponse);

        mockMvc.perform(MockMvcRequestBuilders
            .post(TEST_REQUEST_FOR_ADMIN)
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtils.convertObjectToJsonBytes(measurementRegistrationForAdminRequest))
        ).andExpect(status().isOk());
    }

    @Test
    public void getMeasurementResult_WhenCalled_expectCorrectResponse() throws Exception {

        final MeasurementHistoryResponse measurementHistoryResponse = MeasurementHistoryResponse.builder()
            .measurementServerId(DEFAULT_MEASUREMENT_SERVER_ID)
            .build();
        final MeasurementServer measurementServer = MeasurementServer.builder()
            .name(DEFAULT_ADVERTISED_NAME).build();

        when(measurementService.getMeasurementDetailByUuid(DEFAULT_UUID))
            .thenReturn(measurementHistoryResponse);
        when(measurementServerService.getMeasurementServerById(DEFAULT_MEASUREMENT_SERVER_ID))
            .thenReturn(measurementServer);

        mockMvc
            .perform(MockMvcRequestBuilders.get(MEASUREMENT_RESULT_BY_UUID, DEFAULT_UUID))
            .andExpect(status().isOk());
    }

    @Test
    public void getMeasurementStatsForGeneralUserPortal_WhenCallWithWrongTimeZone_expect400() throws Exception {
        DayOfWeek day = DayOfWeek.FRIDAY;
        mockMvc
            .perform(
                MockMvcRequestBuilders.get(MEASUREMENT_STATS_FOR_GENERAL_USER_PORTAL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("zone", "UT123C")
                    .param("dayOfWeek", day.toString())
            )
            .andExpect(status().is4xxClientError());
    }

    private MeasurementRegistrationResponse getDefaultResponse() {
        return MeasurementRegistrationResponse
            .builder()
            .testUuid(DEFAULT_UUID)
            .resultUrl(DEFAULT_TEST_RESULT_URL)
            .resultQosUrl(DEFAULT_QOS_TEST_RESULT_URL)
            .testDuration(DEFAULT_TEST_DURATION)
            .testServerName(DEFAULT_MEASUREMENT_SERVER_NAME)
            .testWait(DEFAULT_TEST_WEIGHT)
            .testServerAddress(DEFAULT_MEASUREMENT_SERVER_ADDRESS)
            .testNumThreads(DEFAULT_NUM_TEST_THREADS)
            .testServerPort(DEFAULT_MEASUREMENT_SERVER_PORT)
            .testServerEncryption(DEFAULT_IS_MEASUREMENT_SERVER_ENCRYPTED)
            .testToken(DEFAULT_MEASUREMENT_SERVER_TOKEN)
            .testNumPings(DEFAULT_TEST_NUM_PINGS)
            .testId(DEFAULT_TEST_ID)
            .build();
    }

    private MeasurementRegistrationForProbeRequest getDefaultMeasurementServerRequest() {
        return MeasurementRegistrationForProbeRequest
            .builder()
            .client(DEFAULT_CLIENT)
            .port(DEFAULT_PROBE_PORT_INT)
            .isOnNet(true)
            .language(DEFAULT_LANGUAGE)
            .timezone(DEFAULT_TIME_ZONE)
            .uuid(DEFAULT_UUID)
            .build();
    }

    MeasurementStatsForGeneralUserPortalResponse getDefaultMeasurementStatsForGeneralUserPortalResponse() {
        return MeasurementStatsForGeneralUserPortalResponse.builder()
            .today(DEFAULT_TODAY_AMOUNT_OF_MEASUREMENTS)
            .thisWeek(DEFAULT_THIS_WEEK_AMOUNT_OF_MEASUREMENTS)
            .thisMonth(DEFAULT_THIS_MONTH_AMOUNT_OF_MEASUREMENTS)
            .thisYear(DEFAULT_THIS_YEAR_AMOUNT_OF_MEASUREMENTS)
            .build();
    }

}

