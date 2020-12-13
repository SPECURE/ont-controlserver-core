package com.specture.core.request;

import com.specture.core.enums.ClientType;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Builder
@Data
@Getter
public class ClientLocationRequest {
    @NotNull
    private ClientType client;
    private String language;
    private LocationRequest location;
}
