package com.specure.core.model.internal;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JiraRequestFieldsValue {
    private final String summary;
    private final String description;
}
