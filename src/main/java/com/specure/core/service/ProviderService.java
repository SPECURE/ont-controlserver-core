package com.specure.core.service;

import com.specure.core.model.Provider;
import com.specure.core.request.ProviderRequest;
import com.specure.core.response.ProviderResponse;

import java.util.List;

public interface ProviderService {
    List<ProviderResponse> getAllProviders();
    List<Provider> getByIds(List<Long> ids);
    void createNewProvider(ProviderRequest providerRequest);
    void updateProvider(ProviderRequest providerRequest);
    boolean isExist(Long providerId);
    long getDefault();
    Provider getProviderById(long id);
}
