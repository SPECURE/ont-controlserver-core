package com.specture.core.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static com.specture.core.constant.ErrorMessage.PROVIDER_ID_REQUIRED;

@Builder
@Data
public class ProviderRequest {
    @NotNull(message = PROVIDER_ID_REQUIRED)
    private Long id;
    private String name;
}
