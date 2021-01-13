package com.specure.core.service.impl;

import com.specure.core.enums.QosMeasurement;
import com.specure.core.model.Client;
import com.specure.core.repository.ClientRepository;
import com.specure.core.request.SettingRequest;
import com.specure.core.response.settings.*;
import com.specure.core.service.SettingsService;
import com.specure.core.response.settings.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SettingsServiceImpl implements SettingsService {

    private final ClientRepository clientRepository;

    @Override
    public SettingsResponse getSettingsByRequest(SettingRequest settingRequest) {
        String uuid = UUID.randomUUID().toString();
        clientRepository.save(Client.builder().uuid(uuid).build());
        AdvertisedSpeedOption advertisedSpeedOption1 = AdvertisedSpeedOption.builder()
                .enabled(true)
                .maxSpeedDownKbps(100000)
                .maxSpeedUpKbps(100000)
                .minSpeedDownKbps(0)
                .minSpeedUpKbps(0)
                .name("xdsl")
                .uid(1)
                .build();
        AdvertisedSpeedOption advertisedSpeedOption2 = AdvertisedSpeedOption.builder()
                .enabled(true)
                .maxSpeedDownKbps(1000000)
                .maxSpeedUpKbps(1000000)
                .minSpeedDownKbps(0)
                .minSpeedUpKbps(0)
                .name("ethernet")
                .uid(2)
                .build();
        AdvertisedSpeedOption advertisedSpeedOption3 = AdvertisedSpeedOption.builder()
                .enabled(true)
                .maxSpeedDownKbps(108000)
                .maxSpeedUpKbps(400000)
                .minSpeedDownKbps(0)
                .minSpeedUpKbps(0)
                .name("docsis")
                .uid(3)
                .build();

        List<AdvertisedSpeedOption> advertisedSpeedOptionList = List.of(advertisedSpeedOption1, advertisedSpeedOption2, advertisedSpeedOption3);

        List<QosMeasurementTypeDescription> qosMeasurementTypeDescriptionList = List.of(
                QosMeasurementTypeDescription.builder()
                        .name(QosMeasurement.WEBSITE.getDescription())
                        .testType(QosMeasurement.WEBSITE).build(),
                QosMeasurementTypeDescription.builder()
                        .name(QosMeasurement.HTTP_PROXY.getDescription())
                        .testType(QosMeasurement.HTTP_PROXY).build(),
                QosMeasurementTypeDescription.builder()
                        .name(QosMeasurement.NON_TRANSPARENT_PROXY.getDescription())
                        .testType(QosMeasurement.NON_TRANSPARENT_PROXY).build(),
                QosMeasurementTypeDescription.builder()
                        .name(QosMeasurement.DNS.getDescription())
                        .testType(QosMeasurement.DNS).build(),
                QosMeasurementTypeDescription.builder()
                        .name(QosMeasurement.TCP.getDescription())
                        .testType(QosMeasurement.TCP).build(),
                QosMeasurementTypeDescription.builder()
                        .name(QosMeasurement.UDP.getDescription())
                        .testType(QosMeasurement.UDP).build(),
                QosMeasurementTypeDescription.builder()
                        .name(QosMeasurement.VOIP.getDescription())
                        .testType(QosMeasurement.VOIP).build(),
                QosMeasurementTypeDescription.builder()
                        .name(QosMeasurement.TRACEROUTE.getDescription())
                        .testType(QosMeasurement.TRACEROUTE).build()
        );

        SettingResponse settingResponse = SettingResponse.builder()
                .advertisedSpeedOption(advertisedSpeedOptionList)
                .history(HistorySettingsResponse.builder()
                        .devices(Collections.emptyList())
                        .networks(Collections.emptyList())
                        .build())
                .mapServer(MapServerSettingsResponse.builder()
                        .host("nettest.org")
                        .port(443)
                        .ssl(true)
                        .build())
                .qosTestTypeDesc(qosMeasurementTypeDescriptionList)
                .surveySettings(SurveySettingsResponse.builder()
                        .dateStarted(1520153008000L)
                        .isActiveService(false)
                        .surveyUrl("null")
                        .build())
                .urls(UrlsResponse.builder()
                        .controlIpv4Only("c01ipv4.nettest.org")
                        .controlIpv6Only("c01ipv6.nettest.org")
                        .openDataPrefix("https://nettest.org/en/opentest/")
                        .statistics("https://nettest.org/en/statistics")
                        .urlIpv4Check("https://c01ipv4.nettest.org/RMBTControlServer/ip")
                        .urlIpv6Check("https://c01ipv6.nettest.org/RMBTControlServer/ip")
                        .build())
                .uuid(uuid)
                .versions(VersionsResponse.builder()
                        .controlServerVersion("master_build_ver.2.6.0-101-ga1f28720b")
                        .build())
                .build();

        return SettingsResponse.builder()
                .settings(Collections.singletonList(settingResponse))
                .errors(Collections.emptyList())
                .build();
    }
}
