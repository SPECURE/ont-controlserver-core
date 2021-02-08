package com.specure.core.service;

import com.specure.core.model.Client;
import com.specure.core.request.MobileSettingsRequest;

public interface ClientService {

    Client updateClientSettings(MobileSettingsRequest request);
}
