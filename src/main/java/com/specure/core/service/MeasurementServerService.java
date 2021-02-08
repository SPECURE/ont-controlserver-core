package com.specure.core.service;

import com.specure.core.enums.MeasurementType;
import com.specure.core.model.MeasurementServer;
import com.specure.core.model.internal.DataForMeasurementRegistration;
import com.specure.core.request.*;
import com.specure.core.response.MeasurementServerResponse;
import com.specure.core.response.MeasurementServerResponseForSettings;
import com.specure.core.response.NearestMeasurementServersResponse;

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

    List<MeasurementServerResponseForSettings> getServers(List<MeasurementType> serverTypes);
}
