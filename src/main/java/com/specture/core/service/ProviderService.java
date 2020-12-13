package com.specture.core.service;

import com.specture.core.model.Provider;
import com.specture.core.request.ProviderRequest;
import com.specture.core.response.ProviderResponse;

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
