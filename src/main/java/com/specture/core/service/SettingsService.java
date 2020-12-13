package com.specture.core.service;

import com.specture.core.request.SettingRequest;
import com.specture.core.response.settings.SettingsResponse;

public interface SettingsService {
    SettingsResponse getSettingsByRequest(SettingRequest settingRequest);
}
