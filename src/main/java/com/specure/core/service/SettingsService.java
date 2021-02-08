package com.specure.core.service;

import com.specure.core.request.AdminSettingsRequest;
import com.specure.core.request.MobileSettingsRequest;
import com.specure.core.request.SettingRequest;
import com.specure.core.response.settings.MobileSettingsResponse;
import com.specure.core.response.settings.SettingsResponse;

public interface SettingsService {
    SettingsResponse getSettingsByRequest(SettingRequest settingRequest);

    MobileSettingsResponse getMobileSettings(MobileSettingsRequest mobileSettingsRequest);

    void setMobileSettings(AdminSettingsRequest adminSettingsRequest);
}
