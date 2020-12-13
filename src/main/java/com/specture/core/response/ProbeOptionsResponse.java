package com.specture.core.response;

import com.specture.core.enums.Status;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ProbeOptionsResponse {
    private List<TypeResponse> typeOptions;
    private List<Status> statusOptions;
}
