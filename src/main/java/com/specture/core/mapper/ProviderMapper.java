package com.specture.core.mapper;

import com.specture.core.model.Provider;
import com.specture.core.request.ProviderRequest;
import com.specture.core.response.ProviderResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProviderMapper {

    ProviderResponse providerToProviderResponse(Provider provider);

    Provider providerRequestToProvider(ProviderRequest provider);

}