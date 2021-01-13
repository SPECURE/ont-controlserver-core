package com.specure.core.mapper;

import com.specure.core.model.Provider;
import com.specure.core.request.ProviderRequest;
import com.specure.core.response.ProviderResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProviderMapper {

    ProviderResponse providerToProviderResponse(Provider provider);

    Provider providerRequestToProvider(ProviderRequest provider);

}