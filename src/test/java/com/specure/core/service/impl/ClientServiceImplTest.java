package com.specure.core.service.impl;

import com.specure.core.TestConstants;
import com.specure.core.model.Client;
import com.specure.core.repository.ClientRepository;
import com.specure.core.request.MobileSettingsRequest;
import com.specure.core.service.ClientService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ClientServiceImplTest {

    private ClientService clientService;

    @MockBean
    private ClientRepository clientRepository;
    @MockBean
    private UUIDGenerator uuidGenerator;

    @Mock
    private MobileSettingsRequest mobileSettingsRequest;
    @Mock
    private Client client;
    @Captor
    private ArgumentCaptor<Client> clientArgumentCaptor;

    @Before
    public void setUp() {
        clientService = new ClientServiceImpl(clientRepository, uuidGenerator);
    }

    @Test
    public void updateClientSettings_whenClientExistAndConditionAccepted_expectUpdated() {

        when(mobileSettingsRequest.getUuid()).thenReturn(UUID.fromString(TestConstants.DEFAULT_CLIENT_UUID));
        when(mobileSettingsRequest.getTermsAndConditionsAcceptedVersion()).thenReturn(TestConstants.DEFAULT_TERM_AND_CONDITION_VERSION);
        when(clientRepository.findByUuid(TestConstants.DEFAULT_CLIENT_UUID)).thenReturn(Optional.of(client));


        clientService.updateClientSettings(mobileSettingsRequest);

        verify(clientRepository).save(client);
        verify(client).setTermsAndConditionsAcceptedVersion(TestConstants.DEFAULT_TERM_AND_CONDITION_VERSION);
    }

    @Test
    public void updateClientSettings_whenClientNotExistAndConditionAccepted_expectCreated() {
        when(mobileSettingsRequest.getUuid()).thenReturn(UUID.fromString(TestConstants.DEFAULT_CLIENT_UUID));
        when(mobileSettingsRequest.getTermsAndConditionsAcceptedVersion()).thenReturn(TestConstants.DEFAULT_TERM_AND_CONDITION_VERSION);
        when(clientRepository.findByUuid(TestConstants.DEFAULT_CLIENT_UUID)).thenReturn(Optional.empty());
        when(uuidGenerator.generateUUID()).thenReturn(TestConstants.DEFAULT_CLIENT_UUID_GENERATED);
        when(mobileSettingsRequest.getType()).thenReturn(TestConstants.DEFAULT_CLIENT_TYPE);


        clientService.updateClientSettings(mobileSettingsRequest);

        verify(clientRepository).save(clientArgumentCaptor.capture());
        assertEquals(TestConstants.DEFAULT_TERM_AND_CONDITION_VERSION, clientArgumentCaptor.getValue().getTermsAndConditionsAcceptedVersion());
        assertEquals(TestConstants.DEFAULT_FLAG_TRUE, clientArgumentCaptor.getValue().getTermsAndConditionsAccepted());
        assertEquals(TestConstants.DEFAULT_CLIENT_UUID_GENERATED.toString(), clientArgumentCaptor.getValue().getUuid());
    }
}