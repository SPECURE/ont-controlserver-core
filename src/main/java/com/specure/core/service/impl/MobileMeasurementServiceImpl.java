package com.specure.core.service.impl;

import com.specure.core.constant.Constants;
import com.specure.core.repository.MobileMeasurementRepository;
import com.specure.core.service.MobileMeasurementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MobileMeasurementServiceImpl implements MobileMeasurementService {

    private final MobileMeasurementRepository mobileMeasurementRepository;

    @Override
    public List<String> getDeviceHistory(Long clientId) {
        var resultList = mobileMeasurementRepository.getDistinctModelByClientId(clientId);
        resultList.replaceAll(t -> Objects.isNull(t) ? Constants.UNKNOWN_DEVICE : t);
        return resultList;
    }

    @Override
    public List<String> getGroupNameByClientId(Long clientId) {
        return mobileMeasurementRepository.getDistinctGroupNameByClientId(clientId);
    }
}
