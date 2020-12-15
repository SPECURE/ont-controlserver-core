package com.specture.core.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProviderResponse {
    private Long id;
    private String name;
}
