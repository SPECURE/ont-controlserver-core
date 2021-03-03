package com.specure.core.service;

import com.specure.core.model.AdHocCampaign;
import com.specure.core.model.Measurement;
import com.specure.core.model.TimeSlot;
import com.specure.core.model.internal.DataForMeasurementRegistration;
import com.specure.core.request.MeasurementRequest;
import com.specure.core.response.MeasurementHistoryResponse;
import com.specure.core.response.MeasurementRegistrationResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Clock;
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
    Optional<Measurement> getMeasurementByToken(String token);
    List<Measurement> getLastSuccessfulMeasurementByIds(List<Long> ids);
    Page<Measurement> findAll(Pageable pageable);
    void deleteByOpenUUID(String uuid);
    void deleteByAdHocCampaign(AdHocCampaign campaign);
    long deleteByProbeId(String id);
    long deleteByServerId(Long id);
}
