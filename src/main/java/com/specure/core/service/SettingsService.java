package com.specure.core.service;

import com.specure.core.request.SettingRequest;
import com.specure.core.response.settings.SettingsResponse;

public interface SettingsService {
    SettingsResponse getSettingsByRequest(SettingRequest settingRequest);
}
