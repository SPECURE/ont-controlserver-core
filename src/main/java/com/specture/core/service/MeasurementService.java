package com.specture.core.service;

import com.specture.core.model.Measurement;
import com.specture.core.model.TimeSlot;
import com.specture.core.model.internal.DataForMeasurementRegistration;
import com.specture.core.request.MeasurementRequest;
import com.specture.core.response.MeasurementHistoryResponse;
import com.specture.core.response.MeasurementRegistrationResponse;
import com.specture.core.response.MeasurementStatsForGeneralUserPortalResponse;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MeasurementService {
    void setClock(Clock clock);
    void saveMeasurement(MeasurementRequest measurementRequest);
    Measurement partialUpdateMeasurementFromProbeResult(MeasurementRequest measurementRequest);
    MeasurementRegistrationResponse registerMeasurement(DataForMeasurementRegistration dataForMeasurementRegistration, Map<String, String> headers);
    TimeSlot getTimeSlot(long now);
    List<Measurement> findLastMeasurementsForMeasurementServerIds(List<Long> ids);
    MeasurementHistoryResponse getMeasurementDetailByUuid(String uuid);
    Measurement findByOpenTestUuid(String uuid);
    MeasurementStatsForGeneralUserPortalResponse getMeasurementStatsForGeneralUserPortalResponse(ZoneId zoneId, DayOfWeek dayOfWeek);
    Optional<Measurement> getMeasurementByToken(String token);
    List<Measurement> getLastSuccessfulMeasurementByIds(List<Long> ids);
}
