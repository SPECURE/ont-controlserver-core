package com.specture.core.request;

import com.specture.core.enums.ProbeType;
import com.specture.core.enums.Status;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;

import static com.specture.core.constant.ErrorMessage.*;

@Builder
@Data
public class ProbeRequest {

    @NotBlank(message = PROBE_ID_REQUIRED)
    @Size(max = 31, message = PROBE_ID_MAX_SIZE)
    private String id;

    @NotNull(message = PROBE_TYPE_REQUIRED)
    private ProbeType type;

    @NotNull(message = PROBE_STATUS_REQUIRED)
    private Status status;

    @NotNull(message = PROBE_PURPOSE_REQUIRED)
    private String probePurpose;

    @Size(max = 63, message = PROBE_OPERATOR_MAX_SIZE)
    private String operator;

    @Size(max = 255, message = COMMENT_MAX_SIZE)
    private String comment;

    @Max(value = 8, message = PROBE_MODEM_COUNT_MAX_SIZE)
    @Min(value = 0, message = PROBE_MODEM_COUNT_MAX_SIZE)
    private Integer modemCount;

    @Max(value = 8, message = PROBE_FIXED_PORT_RANGE)
    @Min(value = 0, message = PROBE_FIXED_PORT_RANGE)
    private Integer fixedPortNumber;

    @Max(value = 8, message = PROBE_MOBILE_PORT_RANGE)
    @Min(value = 0, message = PROBE_MOBILE_PORT_RANGE)
    private Integer mobilePortNumber;

}
