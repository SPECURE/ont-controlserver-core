package com.specture.core.controller;

import com.specture.core.constant.URIConstants;
import com.specture.core.request.ProviderRequest;
import com.specture.core.response.ProviderResponse;
import com.specture.core.service.ProviderService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProviderController {
    private final ProviderService providerService;

    @ApiOperation("Get list of all providers")
    @GetMapping(URIConstants.PROVIDERS)
    public List<ProviderResponse> getAllProviders() {
        return providerService.getAllProviders();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Create new provider")
    @PostMapping(URIConstants.PROVIDERS)
    public void createNewProvider(@Validated @RequestBody ProviderRequest providerRequest) {
        providerService.createNewProvider(providerRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Update provider")
    @PutMapping(URIConstants.PROVIDERS)
    public void updateProvider(@Validated @RequestBody ProviderRequest providerRequest) {
        providerService.updateProvider(providerRequest);
    }
}
