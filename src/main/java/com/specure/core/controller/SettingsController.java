package com.specure.core.controller;

import com.specure.core.constant.URIConstants;
import com.specure.core.request.SettingRequest;
import com.specure.core.response.settings.SettingsResponse;
import com.specure.core.service.SettingsService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SettingsController {

    final SettingsService settingsService;

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Provide settings for web client.")
    @PostMapping(URIConstants.SETTINGS)
    public SettingsResponse getSettingsForWevClient(@Validated @RequestBody SettingRequest settingRequest) {
        return settingsService.getSettingsByRequest(settingRequest);
    }
}
