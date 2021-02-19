package com.specure.core.mapper.impl;

import com.specure.core.mapper.SignalMapper;
import com.specure.core.model.MobileMeasurement;
import com.specure.core.response.SignalMeasurementResponse;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

@Service
public class SignalMapperImpl implements SignalMapper {

    @Override
    public SignalMeasurementResponse signalToSignalMeasurementResponse(MobileMeasurement mobileMeasurement) {
        return SignalMeasurementResponse.builder()
                .testUuid(mobileMeasurement.getUuid())
                .userUuid(mobileMeasurement.getClient().getUuid())
                .technology(mobileMeasurement.getNetworkGroupName() == null ? null : mobileMeasurement.getNetworkGroupName().getLabelEn())
                .testType("Regular") //TODO temp because of new dedicated mode
                .duration(mobileMeasurement.getDuration())
                .time(ZonedDateTime.ofInstant(mobileMeasurement.getTime().toInstant(), ZoneId.of("UTC"))) //TODO update with user location after test request done
                .build();
    }
}
