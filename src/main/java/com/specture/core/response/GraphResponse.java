package com.specture.core.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class GraphResponse {
    private final String field;
    private final List<ColumnResponse> columns;
}
