package com.specure.core.request;

import com.specure.core.constant.ErrorMessage;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Builder
@Data
public class ProviderRequest {
    @NotNull(message = ErrorMessage.PROVIDER_ID_REQUIRED)
    private Long id;
    private String name;
}
