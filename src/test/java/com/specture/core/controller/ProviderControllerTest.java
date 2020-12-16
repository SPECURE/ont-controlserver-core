package com.specture.core.controller;

import com.specture.core.TestUtils;
import com.specture.core.advice.ControllerErrorAdvice;
import com.specture.core.exception.ProviderAlreadyExistsException;
import com.specture.core.exception.ProviderNotFoundException;
import com.specture.core.request.ProviderRequest;
import com.specture.core.response.ProviderResponse;
import com.specture.core.service.ProviderService;
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

import java.util.List;

import static com.specture.core.TestConstants.DEFAULT_ID;
import static com.specture.core.TestConstants.DEFAULT_PROVIDER_NAME;
import static com.specture.core.constant.URIConstants.PROVIDERS;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class ProviderControllerTest {

    @MockBean
    private ProviderService providerService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        ProviderController providerController = new ProviderController(providerService);
        mockMvc = MockMvcBuilders.standaloneSetup(providerController)
            .setControllerAdvice(new ControllerErrorAdvice())
            .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
            .setViewResolvers((viewName, locale) -> new MappingJackson2JsonView())
            .build();
    }

    @Test
    public void getAllProviders_whenCall_ExpectCorrectResponse() throws Exception {
        ProviderResponse providerResponse = ProviderResponse.builder()
            .name(DEFAULT_PROVIDER_NAME)
            .build();
        List<ProviderResponse> response = List.of(providerResponse);
        when(providerService.getAllProviders()).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.get(PROVIDERS)).andExpect(status().isOk());
    }

    @Test
    public void createProvider_whenCall_ExpectCorrectResponse() throws Exception {
        ProviderRequest providerRequest = getDefaultProviderRequest();
        mockMvc.perform(MockMvcRequestBuilders.post(PROVIDERS)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(TestUtils.convertObjectToJsonBytes(providerRequest))
        ).andExpect(status().isCreated());
    }

    @Test
    public void updateProvider_whenCall_ExpectCorrectResponse() throws Exception {
        ProviderRequest providerRequest = getDefaultProviderRequest();
        mockMvc.perform(MockMvcRequestBuilders.put(PROVIDERS)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(TestUtils.convertObjectToJsonBytes(providerRequest))
        ).andExpect(status().isOk());
    }

    @Test
    public void updateProvider_WhenCallAndProviderDoesnExist_ExpectBadRequest() throws Exception {
        ProviderRequest providerRequest = getDefaultProviderRequest();
        doThrow(new ProviderNotFoundException(DEFAULT_ID))
            .when(providerService)
            .updateProvider(providerRequest);

        mockMvc.perform(MockMvcRequestBuilders.put(PROVIDERS)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(TestUtils.convertObjectToJsonBytes(providerRequest))
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void createProvider_WhenCallWithExistedProviderId_ExpectBadRequest() throws Exception {
        ProviderRequest providerRequest = getDefaultProviderRequest();
        doThrow(new ProviderAlreadyExistsException(DEFAULT_ID))
            .when(providerService)
            .updateProvider(providerRequest);

        mockMvc.perform(MockMvcRequestBuilders.put(PROVIDERS)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(TestUtils.convertObjectToJsonBytes(providerRequest))
        ).andExpect(status().isBadRequest());
    }

    private ProviderRequest getDefaultProviderRequest() {
        return ProviderRequest.builder()
            .id(DEFAULT_ID)
            .name(DEFAULT_PROVIDER_NAME)
            .build();
    }
}
