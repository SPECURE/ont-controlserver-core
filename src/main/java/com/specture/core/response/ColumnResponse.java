package com.specture.core.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ColumnResponse {
    private final String column;
    private final List<RowResponse> rows;
}
