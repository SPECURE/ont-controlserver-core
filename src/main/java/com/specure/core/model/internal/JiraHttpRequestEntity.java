package com.specure.core.model.internal;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JiraHttpRequestEntity {
    private final String serviceDeskId;
    private final String requestTypeId;
    private final JiraRequestFieldsValue requestFieldValues;
}
