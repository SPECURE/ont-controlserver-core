package com.specure.core.request;

import com.specure.core.constant.ErrorMessage;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.specure.core.constant.Constants.ES_SEARCH_VALIDATION_REGEX;
import static com.specure.core.constant.ErrorMessage.ADVERTISED_NAME_CHARS_REQUIRED;

@Builder
@Data
public class ProviderRequest {
    @NotNull(message = ErrorMessage.PROVIDER_ID_REQUIRED)
    private Long id;
    @Pattern(regexp = ES_SEARCH_VALIDATION_REGEX, message = ADVERTISED_NAME_CHARS_REQUIRED)
    private String name;
}
