package com.specture.core.service.impl;

import com.specture.core.service.ProviderService;
import com.specture.core.config.MeasurementServerConfig;
import com.specture.core.exception.ProviderAlreadyExistsException;
import com.specture.core.exception.ProviderNotFoundException;
import com.specture.core.mapper.ProviderMapper;
import com.specture.core.model.Provider;
import com.specture.core.repository.ProviderRepository;
import com.specture.core.request.ProviderRequest;
import com.specture.core.response.ProviderResponse;
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
