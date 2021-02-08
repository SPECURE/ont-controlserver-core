package com.specure.core.service;


import java.util.List;

public interface MobileMeasurementService {

    List<String> getDeviceHistory(Long clientId);

    List<String> getGroupNameByClientId(Long clientId);
}
