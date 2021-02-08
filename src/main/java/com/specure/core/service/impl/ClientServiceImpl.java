package com.specure.core.service.impl;

import com.specure.core.model.Client;
import com.specure.core.repository.ClientRepository;
import com.specure.core.request.MobileSettingsRequest;
import com.specure.core.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final UUIDGenerator uuidGenerator;

    @Override
    public Client updateClientSettings(MobileSettingsRequest request) {
        LocalDateTime now = LocalDateTime.now();
        boolean isTermAndConditionAccepted = isTermAndConditionAccepted(request);
        Client client = clientRepository.findByUuid(request.getUuid().toString())
                .orElse(null);
        if (Objects.nonNull(client)) {
            if (client.getTermsAndConditionsAcceptedVersion() < request.getTermsAndConditionsAcceptedVersion()) {
                client.setTermsAndConditionsAcceptedVersion(request.getTermsAndConditionsAcceptedVersion());
                client.setTermsAndConditionsAcceptedTimestamp(now);
            }
            client.setLastSeen(now);

        } else if (isTermAndConditionAccepted) {
            client = new Client();
            client.setUuid(uuidGenerator.generateUUID().toString());
            client.setClientType(request.getType());
            client.setTermsAndConditionsAccepted(isTermAndConditionAccepted);
            client.setTermsAndConditionsAcceptedVersion(request.getTermsAndConditionsAcceptedVersion());
            client.setLastSeen(now);
        }
        return clientRepository.save(client);
    }

    private boolean isTermAndConditionAccepted(MobileSettingsRequest mobileSettingsRequest) {
        if (mobileSettingsRequest.getTermsAndConditionsAcceptedVersion() > 0) {
            return true;
        } else {
            return mobileSettingsRequest.isTermsAndConditionsAccepted();
        }
    }
}
