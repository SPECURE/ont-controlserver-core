package com.specure.core.service.impl;

import com.specure.core.service.ProviderService;
import com.specure.core.config.MeasurementServerConfig;
import com.specure.core.exception.ProviderAlreadyExistsException;
import com.specure.core.exception.ProviderNotFoundException;
import com.specure.core.mapper.ProviderMapper;
import com.specure.core.model.Provider;
import com.specure.core.repository.ProviderRepository;
import com.specure.core.request.ProviderRequest;
import com.specure.core.response.ProviderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProviderServiceImpl implements ProviderService {

    private final ProviderRepository providerRepository;
    private final ProviderMapper providerMapper;
    private final MeasurementServerConfig measurementServerConfig;

    @Override
    public List<ProviderResponse> getAllProviders() {
        return providerRepository.findAll()
                .stream()
                .map(providerMapper::providerToProviderResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<Provider> getByIds(List<Long> ids) {
        return providerRepository.findAllById(ids);
    }

    @Override
    public void createNewProvider(ProviderRequest providerRequest) {
        Provider provider = providerMapper.providerRequestToProvider(providerRequest);
        Long id = provider.getId();
        if (providerRepository.existsById(id)) {
            throw new ProviderAlreadyExistsException(id);
        }
        providerRepository.save(provider);
    }

    @Override
    public void updateProvider(ProviderRequest providerRequest) {
        Provider provider = providerMapper.providerRequestToProvider(providerRequest);
        Long id = provider.getId();
        if (! providerRepository.existsById(id)) {
            throw new ProviderNotFoundException(id);
        }
        providerRepository.save(provider);
    }

    @Override
    public boolean isExist(Long providerId) {
        return providerRepository.existsById(providerId);
    }

    @Override
    public long getDefault() {
        return measurementServerConfig.getDefaultProviderId();

    }

    @Override
    public Provider getProviderById(long id) {
        return providerRepository
                .findById(id)
                .orElseThrow(() -> new ProviderNotFoundException(id));
    }

}
