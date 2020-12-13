package com.specture.core.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RowResponse {
    private final String name;
    private final long value;
}
