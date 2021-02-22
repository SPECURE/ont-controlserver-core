package com.specure.core.service.impl;

import com.specure.core.config.MeasurementServerConfig;
import com.specure.core.constant.MeasurementServerConstants;
import com.specure.core.enums.MeasurementType;
import com.specure.core.exception.MeasurementServerNotFoundException;
import com.specure.core.mapper.MeasurementServerMapper;
import com.specure.core.model.Measurement;
import com.specure.core.model.MeasurementServer;
import com.specure.core.model.MeasurementServerDescription;
import com.specure.core.model.Provider;
import com.specure.core.model.internal.DataForMeasurementRegistration;
import com.specure.core.repository.MeasurementServerRepository;
import com.specure.core.request.*;
import com.specure.core.response.MeasurementServerForWebResponse;
import com.specure.core.response.MeasurementServerResponse;
import com.specure.core.response.MeasurementServerResponseForSettings;
import com.specure.core.response.NearestMeasurementServersResponse;
import com.specure.core.service.JiraApiService;
import com.specure.core.service.MeasurementServerService;
import com.specure.core.service.MeasurementService;
import com.specure.core.service.ProviderService;
import com.specure.core.utils.profiling.Profiling;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service("basicMeasurementServerService")
public class MeasurementServerServiceImpl implements MeasurementServerService {

    private final MeasurementServerRepository measurementServerRepository;
    private final MeasurementServerMapper measurementServerMapper;
    private final ProviderService providerService;
    private final MeasurementService measurementService;
    private final JiraApiService jiraApiService;
    private final MeasurementServerConfig measurementServerConfig;

    public MeasurementServerServiceImpl(MeasurementServerRepository measurementServerRepository, MeasurementServerMapper measurementServerMapper, ProviderService providerService,
                                        MeasurementService measurementService, JiraApiService jiraApiService, MeasurementServerConfig measurementServerConfig) {
        this.measurementServerRepository = measurementServerRepository;
        this.measurementServerMapper = measurementServerMapper;
        this.providerService = providerService;
        this.measurementService = measurementService;
        this.jiraApiService = jiraApiService;
        this.measurementServerConfig = measurementServerConfig;
    }

    @Override
    public DataForMeasurementRegistration getDataFromProbeMeasurementRegistrationRequest(MeasurementRegistrationForProbeRequest measurementRegistrationForProbeRequest) {
        return null;
    }

    @Override
    public DataForMeasurementRegistration getMeasurementServerForWebClient(MeasurementRegistrationForWebClientRequest measurementRegistrationForWebClientRequest) {

        // it's only mock
        // get first measurement server
        // TODO find nearest server

        MeasurementServer measurementServer = measurementServerRepository
                .findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new MeasurementServerNotFoundException(0L));

        return DataForMeasurementRegistration.builder()
                .measurementServer(measurementServer)
                .clientUuid(measurementRegistrationForWebClientRequest.getUuid())
                .measurementType(measurementRegistrationForWebClientRequest.getClient())
                .build();
    }

    @Override
    public List<MeasurementServerResponse> getMeasurementServers(Long providerId) {

        Profiling allGetMeasurementServerMethod = new Profiling();
        allGetMeasurementServerMethod.start();

        Map<Long, Measurement> measurementMap = new HashMap<>();
        Map<Long, Measurement> successfulMeasurementMap = new HashMap<>();

        // 1)
        Profiling findServers = new Profiling();
        List<MeasurementServer> servers;
        findServers.start();
        if (providerId == null) {
            servers = measurementServerRepository.findAll();
        } else {
            var provider = providerService.getProviderById(providerId);
            servers = measurementServerRepository.findByProvider(provider);
        }
        log.info("GET SERVERS PROFILING: 1) find servers {}", findServers.finishAndGetDuration());

        // 2)
        Profiling findLastMeasurements = new Profiling();
        findLastMeasurements.start();
        List<Long> ids = servers.stream().map(MeasurementServer::getId).collect(Collectors.toList());
        var measurements = measurementService.findLastMeasurementsForMeasurementServerIds(ids);
        measurements.forEach(measurement -> measurementMap.put(measurement.getMeasurementServerId(), measurement));
        log.info("GET SERVERS PROFILING: 2) find last measurements {}", findLastMeasurements.finishAndGetDuration());

        // 2.1
        Profiling findLastSuccessfulMeasurements = new Profiling();
        findLastSuccessfulMeasurements.start();
        var successfulMeasurements = measurementService.getLastSuccessfulMeasurementByIds(ids);
        successfulMeasurements.forEach(measurement -> successfulMeasurementMap.put(measurement.getMeasurementServerId(), measurement));
        log.info("GET SERVERS PROFILING: 2) find last successful measurements {}", findLastSuccessfulMeasurements.finishAndGetDuration());

        // 3) post processing
        Profiling postProcessing = new Profiling();
        postProcessing.start();
        List<MeasurementServerResponse> response = servers
                .stream()
                .map(measurementServerMapper::measurementServerToMeasurementServerResponse)
                .collect(Collectors.toList());

        response.forEach(server -> {
            Long id = server.getId();
            if (measurementMap.containsKey(id)) {
                var measurement = measurementMap.get(id);
                boolean flag = (measurement.getSpeedDownload() != null && measurement.getSpeedUpload() != null && measurement.getPingMedian() != null);
                server.setTimeOfLastMeasurement(measurement.getTime());
                server.setLastMeasurementSuccess(flag);
            }
            if (successfulMeasurementMap.containsKey(id)) {
                var lastSuccessfulMeasurement = successfulMeasurementMap.get(id);
                server.setLastSuccessfulMeasurement(lastSuccessfulMeasurement.getTime());
            }
        });
        log.info("GET SERVERS PROFILING: 3) post processing {}", postProcessing.finishAndGetDuration());
        log.info("GET SERVERS PROFILING: total {}", allGetMeasurementServerMethod.finishAndGetDuration());
        return response;
    }

    @Override
    public DataForMeasurementRegistration getMeasurementServerById(MeasurementRegistrationForAdminRequest measurementRegistrationForAdminRequest) {

        Long id = measurementRegistrationForAdminRequest.getMeasurementServerId();

        MeasurementServer serverChosenByAdmin = measurementServerRepository.findById(id)
                .orElseThrow(() -> new MeasurementServerNotFoundException(id));

        return DataForMeasurementRegistration.builder()
                .measurementServer(serverChosenByAdmin)
                .measurementType(measurementRegistrationForAdminRequest.getClient())
                .clientUuid(measurementRegistrationForAdminRequest.getUuid())
                .build();
    }

    @Override
    public void createMeasurementServer(MeasurementServerRequest measurementServerRequest) {
        if(measurementServerRequest.getMeasurementTypeList() == null) //todo remove after ui fixed
            measurementServerRequest.setMeasurementTypeList(Collections.emptySet());
        MeasurementServer measurementServer = measurementServerMapper.measurementServerRequestToMeasurementServer(measurementServerRequest);
        Long providerId = measurementServer.getProvider().getId();
        Provider provider;
        if (providerId == null) {
            providerId = providerService.getDefault();
        }
        provider = providerService.getProviderById(providerId);
        measurementServer.setProvider(provider);
        measurementServerRepository.save(measurementServer);

        jiraApiService.createIssue(MeasurementServerConstants.JIRA_SUMMARY, getDescription(measurementServer));
    }

    @Override
    public void updateMeasurementServer(Long id, MeasurementServerRequest measurementServerRequest) {
        if(measurementServerRequest.getMeasurementTypeList() == null) //todo remove after ui fixed
            measurementServerRequest.setMeasurementTypeList(Collections.emptySet());
        MeasurementServer measurementServer = measurementServerMapper.measurementServerRequestToMeasurementServer(measurementServerRequest);
        measurementServer.setId(id);
        if (measurementServerRequest.getProviderId() == null) {
            measurementServerRequest.setProviderId(providerService.getDefault());
        }
        Provider provider = providerService.getProviderById(measurementServerRequest.getProviderId());
        measurementServer.setProvider(provider);
        MeasurementServer former = measurementServerRepository
                .findById(id)
                .orElseThrow(() -> new MeasurementServerNotFoundException(id));
        MeasurementServerDescription formerDescription = former.getMeasurementServerDescription();
        if (formerDescription != null) {
            MeasurementServerDescription measurementServerDescription = measurementServer.getMeasurementServerDescription();
            measurementServerDescription.setId(formerDescription.getId());
        }
        measurementServerRepository.save(measurementServer);
    }

    @Override
    public NearestMeasurementServersResponse getNearestServers(ClientLocationRequest clientLocationRequest) {

        List<MeasurementServer> all = measurementServerRepository.findAll();

        List<MeasurementServerForWebResponse> serversForWeb = all
                .stream()
                .map(measurementServer -> measurementServerMapper.measurementServersToMeasurementServerForWebResponse(measurementServer, clientLocationRequest.getClient()))
                .collect(Collectors.toList());

        return NearestMeasurementServersResponse.builder()
                .error(Collections.emptyList())
                .servers(serversForWeb)
                .build();
    }

    @Override
    public MeasurementServer getMeasurementServerById(long id) {
        return measurementServerRepository
                .findById(id)
                .orElseThrow(() -> new MeasurementServerNotFoundException(id));
    }

    protected List<MeasurementServer> getMeasurementServerByProviderOnOffNet(Provider provider, Boolean isOnNet) {
        long defaultProviderId = this.measurementServerConfig.getDefaultProviderId();
        Provider defaultProvider = this.providerService.getProviderById(defaultProviderId);
        return isOnNet ? measurementServerRepository.findByProvider(provider)
                : measurementServerRepository.findByProvider(defaultProvider);
    }

    private String getDescription(MeasurementServer measurementServer) {

        String companyName = "unknown";
        MeasurementServerDescription details = measurementServer.getMeasurementServerDescription();

        if (details != null) {
            companyName = details.getCompany();
        }

        String description = String.format(MeasurementServerConstants.JIRA_REPORT_HEADER, companyName);

        description += "\nname: " + measurementServer.getName();
        description += "\nweb address: " + measurementServer.getWebAddress();
        if (measurementServer.getProvider() != null) {
            description += "\nprovider: " + measurementServer.getProvider().getName();
        }

        if (details != null) {
            description += "\ncity: " + details.getCity();
            description += "\nemail: " + details.getEmail();
            description += "\nip address: " + details.getIpAddress();
            description += "\ncomment: " + details.getComment();
        }

        return description;
    }

    @Override
    public void deleteByServerById(long id) {
        measurementServerRepository.deleteById(id);
    }

    @Override
    public List<MeasurementServerResponseForSettings> getServers(List<MeasurementType> serverTypes) {
        return measurementServerRepository.getByActiveTrueAndSelectableTrueAndServerTypeIn(serverTypes).stream()
                .map(measurementServerMapper::measurementServersToMeasurementServerResponseForSettings)
                .collect(Collectors.toList());
    }
}
