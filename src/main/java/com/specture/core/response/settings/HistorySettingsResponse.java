package com.specture.core.response.settings;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class HistorySettingsResponse {
    private List<String> devices;
    private List<String> networks;
}
