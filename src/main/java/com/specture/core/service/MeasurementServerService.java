package com.specture.core.service;

import com.specture.core.model.MeasurementServer;
import com.specture.core.model.internal.DataForMeasurementRegistration;
import com.specture.core.request.*;
import com.specture.core.response.MeasurementServerResponse;
import com.specture.core.response.NearestMeasurementServersResponse;

import java.util.List;

public interface MeasurementServerService {

    DataForMeasurementRegistration getDataFromProbeMeasurementRegistrationRequest(MeasurementRegistrationForProbeRequest measurementRegistrationForProbeRequest);

    DataForMeasurementRegistration getMeasurementServerForWebClient(MeasurementRegistrationForWebClientRequest measurementRegistrationForWebClientRequest);

    List<MeasurementServerResponse> getMeasurementServers(Long providerId);

    DataForMeasurementRegistration getMeasurementServerById(MeasurementRegistrationForAdminRequest measurementRegistrationForAdminRequest);

    void createMeasurementServer(MeasurementServerRequest measurementServerRequest);

    void updateMeasurementServer(Long id, MeasurementServerRequest measurementServerRequest);

    NearestMeasurementServersResponse getNearestServers(ClientLocationRequest clientLocationRequest);

    MeasurementServer getMeasurementServerById(long id);
}
