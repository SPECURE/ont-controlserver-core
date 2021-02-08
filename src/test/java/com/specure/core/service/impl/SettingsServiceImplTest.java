package com.specure.core.service.impl;

import com.specure.core.TestConstants;
import com.specure.core.constant.Config;
import com.specure.core.enums.ClientType;
import com.specure.core.exception.NotSupportedClientVersionException;
import com.specure.core.model.Client;
import com.specure.core.model.Settings;
import com.specure.core.repository.ClientRepository;
import com.specure.core.repository.SettingsRepository;
import com.specure.core.request.AdminSettingsBodyRequest;
import com.specure.core.request.AdminSettingsRequest;
import com.specure.core.request.MobileSettingsRequest;
import com.specure.core.request.SettingRequest;
import com.specure.core.response.MeasurementServerResponseForSettings;
import com.specure.core.response.settings.*;
import com.specure.core.service.ClientService;
import com.specure.core.service.MeasurementServerService;
import com.specure.core.service.MobileMeasurementService;
import com.specure.core.service.SettingsService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class SettingsServiceImplTest {

    @MockBean
    private ClientRepository clientRepository;
    @MockBean
    private ClientService clientService;
    @MockBean
    private SettingsRepository settingsRepository;
    @MockBean
    private MeasurementServerService measurementServerService;
    @MockBean
    private MobileMeasurementService mobileMeasurementService;
    @MockBean
    private UUIDGenerator uuidGenerator;

    @Mock
    private SettingRequest settingRequest;
    @Mock
    private MobileSettingsRequest mobileSettingsRequest;
    @Mock
    private Client client;
    @Mock
    private Client savedClient;
    @Mock
    private Settings settings;
    @Captor
    private ArgumentCaptor<Client> clientArgumentCaptor;
    @Mock
    private AdminSettingsRequest adminSettingsRequest;
    @Mock
    private AdminSettingsBodyRequest adminSettingsBodyRequest;
    @Captor
    private ArgumentCaptor<List<Settings>> settingsArgumentCaptor;

    private SettingsService settingsService;

    @Before
    public void setUp() {
        settingsService = new SettingsServiceImpl(clientRepository, clientService, settingsRepository, mobileMeasurementService, measurementServerService);
    }

    @Test
    public void getSettingsByRequest_correctRequest_settingsNotNull() {
        SettingsResponse settingsResponse = settingsService.getSettingsByRequest(settingRequest);
        verify(clientRepository).save(any());
        assertNotNull(settingsResponse);
    }

    @Test(expected = NotSupportedClientVersionException.class)
    public void getSettings_whenNotSupportedName_expectNotSupportedClientVersionException() {
        when(mobileSettingsRequest.getName()).thenReturn(TestConstants.DEFAULT_TEXT);
        settingsService.getMobileSettings(mobileSettingsRequest);
    }

    @Test
    public void getSettings_whenAndroidPlatform_expectSettingsResponse() {
        var historyDevices = List.of(TestConstants.DEFAULT_HISTORY_DEVICE);
        var historyNetworks = List.of(TestConstants.DEFAULT_HISTORY_NETWORK);
        when(mobileSettingsRequest.getName()).thenReturn(TestConstants.DEFAULT_CLIENT_NAME);
        when(mobileSettingsRequest.getUuid()).thenReturn(UUID.fromString(TestConstants.DEFAULT_CLIENT_UUID));
        when(mobileSettingsRequest.getPlatform()).thenReturn(TestConstants.DEFAULT_ANDROID_PLATFORM);
        when(mobileSettingsRequest.getLanguage()).thenReturn(TestConstants.DEFAULT_LANGUAGE);
        when(mobileSettingsRequest.getTermsAndConditionsAcceptedVersion()).thenReturn(TestConstants.DEFAULT_TERM_AND_CONDITION_VERSION);
        when(client.getTermsAndConditionsAcceptedVersion()).thenReturn(TestConstants.DEFAULT_TERM_AND_CONDITION_VERSION - 1);
        when(clientService.updateClientSettings(mobileSettingsRequest)).thenReturn(savedClient);
        when(savedClient.getUuid()).thenReturn(TestConstants.DEFAULT_CLIENT_UUID.toString());
        when(savedClient.getId()).thenReturn(TestConstants.DEFAULT_ID);
        when(settingsRepository.findAllByLangOrLangIsNullAndKeyIn(TestConstants.DEFAULT_LANGUAGE, Config.SETTINGS_KEYS))
                .thenReturn(getDefaultSettings());
        when(mobileMeasurementService.getDeviceHistory(TestConstants.DEFAULT_ID)).thenReturn(historyDevices);
        when(mobileMeasurementService.getGroupNameByClientId(TestConstants.DEFAULT_ID)).thenReturn(historyNetworks);
        when(measurementServerService.getServers(Config.SERVER_TEST_SERVER_TYPES)).thenReturn(getServerResponseList());
        when(measurementServerService.getServers(Config.SERVER_WS_TEST_SERVER_TYPES)).thenReturn(getServerWsResponseList());
        when(measurementServerService.getServers(Config.SERVER_QOS_TEST_SERVER_TYPES)).thenReturn(getServerQoSResponseList());

        var response = settingsService.getMobileSettings(mobileSettingsRequest);

        assertEquals(TestConstants.DEFAULT_CLIENT_UUID, response.getSettings().get(0).getUuid());
        assertEquals(getAndroidTermAndConditionsResponse(), response.getSettings().get(0).getTermAndConditionsResponse());
        assertEquals(historyDevices, response.getSettings().get(0).getHistory().getDevices());
        assertEquals(historyNetworks, response.getSettings().get(0).getHistory().getNetworks());
        assertEquals(getMapServerResponse(), response.getSettings().get(0).getMapServerResponse());
        assertEquals(getServerResponseList(), response.getSettings().get(0).getServers());
        assertEquals(getServerWsResponseList(), response.getSettings().get(0).getServerWSResponseList());
        assertEquals(getServerQoSResponseList(), response.getSettings().get(0).getServerQoSResponseList());
        assertEquals(getUrlsResponse(), response.getSettings().get(0).getUrls());
    }

    @Test
    public void getSettings_whenClientNotExist_expectSettingsResponse() {
        var historyDevices = List.of(TestConstants.DEFAULT_HISTORY_DEVICE);
        var historyNetworks = List.of(TestConstants.DEFAULT_HISTORY_NETWORK);
        when(uuidGenerator.generateUUID()).thenReturn(TestConstants.DEFAULT_CLIENT_UUID_GENERATED);
        when(mobileSettingsRequest.getName()).thenReturn(TestConstants.DEFAULT_CLIENT_NAME);
        when(mobileSettingsRequest.getUuid()).thenReturn(UUID.fromString(TestConstants.DEFAULT_CLIENT_UUID));
        when(mobileSettingsRequest.getPlatform()).thenReturn(TestConstants.DEFAULT_ANDROID_PLATFORM);
        when(mobileSettingsRequest.getLanguage()).thenReturn(TestConstants.DEFAULT_LANGUAGE);
        when(mobileSettingsRequest.getType()).thenReturn(ClientType.DESKTOP);
        when(mobileSettingsRequest.getTermsAndConditionsAcceptedVersion()).thenReturn(TestConstants.DEFAULT_TERM_AND_CONDITION_VERSION);
        when(client.getTermsAndConditionsAcceptedVersion()).thenReturn(TestConstants.DEFAULT_TERM_AND_CONDITION_VERSION - 1);
        when(clientService.updateClientSettings(mobileSettingsRequest)).thenReturn(savedClient);
        when(savedClient.getUuid()).thenReturn(String.valueOf(TestConstants.DEFAULT_CLIENT_UUID_GENERATED));
        when(savedClient.getId()).thenReturn(TestConstants.DEFAULT_ID);
        when(settingsRepository.findAllByLangOrLangIsNullAndKeyIn(TestConstants.DEFAULT_LANGUAGE, Config.SETTINGS_KEYS))
                .thenReturn(getDefaultSettings());
        when(mobileMeasurementService.getDeviceHistory(TestConstants.DEFAULT_ID)).thenReturn(historyDevices);
        when(mobileMeasurementService.getGroupNameByClientId(TestConstants.DEFAULT_ID)).thenReturn(historyNetworks);
        when(measurementServerService.getServers(Config.SERVER_TEST_SERVER_TYPES)).thenReturn(getServerResponseList());
        when(measurementServerService.getServers(Config.SERVER_WS_TEST_SERVER_TYPES)).thenReturn(getServerWsResponseList());
        when(measurementServerService.getServers(Config.SERVER_QOS_TEST_SERVER_TYPES)).thenReturn(getServerQoSResponseList());

        var response = settingsService.getMobileSettings(mobileSettingsRequest);

        assertEquals(TestConstants.DEFAULT_CLIENT_UUID_GENERATED.toString(), response.getSettings().get(0).getUuid());
        assertEquals(getAndroidTermAndConditionsResponse(), response.getSettings().get(0).getTermAndConditionsResponse());
        assertEquals(historyDevices, response.getSettings().get(0).getHistory().getDevices());
        assertEquals(historyNetworks, response.getSettings().get(0).getHistory().getNetworks());
        assertEquals(getMapServerResponse(), response.getSettings().get(0).getMapServerResponse());
        assertEquals(getServerResponseList(), response.getSettings().get(0).getServers());
        assertEquals(getServerWsResponseList(), response.getSettings().get(0).getServerWSResponseList());
        assertEquals(getServerQoSResponseList(), response.getSettings().get(0).getServerQoSResponseList());
        assertEquals(getUrlsResponse(), response.getSettings().get(0).getUrls());
    }

    @Test
    public void getSettings_whenIOSPlatform_expectSettingsResponse() {
        when(mobileSettingsRequest.getName()).thenReturn(TestConstants.DEFAULT_CLIENT_NAME);
        when(mobileSettingsRequest.getUuid()).thenReturn(UUID.fromString(TestConstants.DEFAULT_CLIENT_UUID));
        when(mobileSettingsRequest.getPlatform()).thenReturn(TestConstants.DEFAULT_IOS_PLATFORM);
        when(mobileSettingsRequest.getLanguage()).thenReturn(TestConstants.DEFAULT_LANGUAGE);
        when(mobileSettingsRequest.getTermsAndConditionsAcceptedVersion()).thenReturn(TestConstants.DEFAULT_TERM_AND_CONDITION_VERSION);
        when(client.getTermsAndConditionsAcceptedVersion()).thenReturn(TestConstants.DEFAULT_TERM_AND_CONDITION_VERSION - 1);
        when(clientService.updateClientSettings(mobileSettingsRequest)).thenReturn(savedClient);
        when(savedClient.getUuid()).thenReturn(String.valueOf(TestConstants.DEFAULT_CLIENT_UUID));
        when(savedClient.getId()).thenReturn(TestConstants.DEFAULT_ID);
        when(settingsRepository.findAllByLangOrLangIsNullAndKeyIn(TestConstants.DEFAULT_LANGUAGE, Config.SETTINGS_KEYS))
                .thenReturn(getDefaultSettings());

        var response = settingsService.getMobileSettings(mobileSettingsRequest);

        assertEquals(TestConstants.DEFAULT_CLIENT_UUID, response.getSettings().get(0).getUuid());
        assertEquals(getIOSTermAndConditionsResponse(), response.getSettings().get(0).getTermAndConditionsResponse());
    }

    @Test
    public void getSettings_whenNotStandardPlatform_expectSettingsResponse() {
        when(mobileSettingsRequest.getName()).thenReturn(TestConstants.DEFAULT_CLIENT_NAME);
        when(mobileSettingsRequest.getUuid()).thenReturn(UUID.fromString(TestConstants.DEFAULT_CLIENT_UUID));
        when(mobileSettingsRequest.getPlatform()).thenReturn(TestConstants.DEFAULT_PLATFORM);
        when(mobileSettingsRequest.getLanguage()).thenReturn(TestConstants.DEFAULT_LANGUAGE);
        when(mobileSettingsRequest.getTermsAndConditionsAcceptedVersion()).thenReturn(TestConstants.DEFAULT_TERM_AND_CONDITION_VERSION);
        when(client.getTermsAndConditionsAcceptedVersion()).thenReturn(TestConstants.DEFAULT_TERM_AND_CONDITION_VERSION - 1);
        when(clientService.updateClientSettings(mobileSettingsRequest)).thenReturn(savedClient);
        when(savedClient.getUuid()).thenReturn(TestConstants.DEFAULT_CLIENT_UUID);
        when(savedClient.getId()).thenReturn(TestConstants.DEFAULT_ID);
        when(settingsRepository.findAllByLangOrLangIsNullAndKeyIn(TestConstants.DEFAULT_LANGUAGE, Config.SETTINGS_KEYS))
                .thenReturn(getDefaultSettings());

        var response = settingsService.getMobileSettings(mobileSettingsRequest);

        assertEquals(TestConstants.DEFAULT_CLIENT_UUID, response.getSettings().get(0).getUuid());
        assertEquals(getDefaultTermAndConditionsResponse(), response.getSettings().get(0).getTermAndConditionsResponse());
    }

    @Test
    public void createSettings_whenSettingsExist_expectSettingsUpdated() {
        when(settingsRepository.findAllByLangOrLangIsNullAndKeyIn(TestConstants.DEFAULT_LANGUAGE, Config.SETTINGS_KEYS))
                .thenReturn(List.of(settings));
        when(settings.getKey()).thenReturn(TestConstants.DEFAULT_SETTINGS_KEY);
        when(adminSettingsRequest.getSettings()).thenReturn(getAdminSettingsBodyRequest());
        when(adminSettingsBodyRequest.getTcUrl()).thenReturn(TestConstants.DEFAULT_TC_URL_VALUE);
        when(adminSettingsRequest.getLanguage()).thenReturn(TestConstants.DEFAULT_LANGUAGE);

        settingsService.setMobileSettings(adminSettingsRequest);

        verify(settings).setValue(TestConstants.DEFAULT_TC_URL_VALUE);
        verify(settingsRepository).saveAll(settingsArgumentCaptor.capture());
        List<Settings> actual = settingsArgumentCaptor.getValue();
        assertEquals(settings, actual.get(0));
        assertEquals("port_map_server", actual.get(1).getKey());
        assertEquals("123", actual.get(1).getValue());
    }

    @Test
    public void createSettings_whenSettingsLongExist_expectSettingsUpdated() {
        when(settingsRepository.findAllByLangOrLangIsNullAndKeyIn(TestConstants.DEFAULT_LANGUAGE, Config.SETTINGS_KEYS))
                .thenReturn(List.of(settings));
        when(settings.getKey()).thenReturn(TestConstants.DEFAULT_SETTINGS_KEY);
        when(adminSettingsRequest.getSettings()).thenReturn(getAdminSettingsBodyRequest());
        when(adminSettingsBodyRequest.getTcUrl()).thenReturn(TestConstants.DEFAULT_TC_URL_VALUE);
        when(adminSettingsRequest.getLanguage()).thenReturn(TestConstants.DEFAULT_LANGUAGE);

        settingsService.setMobileSettings(adminSettingsRequest);

        verify(settings).setValue(TestConstants.DEFAULT_TC_URL_VALUE);
        verify(settingsRepository).saveAll(settingsArgumentCaptor.capture());
        List<Settings> actual = settingsArgumentCaptor.getValue();
        assertEquals(settings, actual.get(0));
        assertEquals("port_map_server", actual.get(1).getKey());
        assertEquals("123", actual.get(1).getValue());
    }

    @Test
    public void createSettings_whenSettingsNotExist_expectSettingsCreated() {
        when(settings.getKey()).thenReturn(TestConstants.DEFAULT_SETTINGS_KEY);
        when(adminSettingsRequest.getSettings()).thenReturn(getAdminSettingsBodyRequest());
        when(adminSettingsBodyRequest.getTcUrl()).thenReturn(TestConstants.DEFAULT_TC_URL_VALUE);
        when(adminSettingsRequest.getLanguage()).thenReturn(TestConstants.DEFAULT_LANGUAGE);

        settingsService.setMobileSettings(adminSettingsRequest);

        verify(settingsRepository).saveAll(settingsArgumentCaptor.capture());
        List<Settings> actual = settingsArgumentCaptor.getValue();
        assertEquals("port_map_server", actual.get(1).getKey());
        assertEquals("123", actual.get(1).getValue());
        assertEquals(2, settingsArgumentCaptor.getValue().size());
        Assert.assertEquals(TestConstants.DEFAULT_SETTINGS_KEY, settingsArgumentCaptor.getValue().get(0).getKey());
        Assert.assertEquals(TestConstants.DEFAULT_TC_URL_VALUE, settingsArgumentCaptor.getValue().get(0).getValue());
        Assert.assertEquals(TestConstants.DEFAULT_LANGUAGE, settingsArgumentCaptor.getValue().get(0).getLang());
    }

    private AdminSettingsBodyRequest getAdminSettingsBodyRequest() {
        return AdminSettingsBodyRequest.builder()
                .tcUrl(TestConstants.DEFAULT_TC_URL_VALUE)
                .port(123L)
                .build();
    }

    private TermAndConditionsResponse getDefaultTermAndConditionsResponse() {
        return TermAndConditionsResponse.builder()
                .version(Long.valueOf(TestConstants.DEFAULT_TC_VERSION_VALUE))
                .url(TestConstants.DEFAULT_TC_URL_VALUE)
                .build();
    }

    private TermAndConditionsResponse getIOSTermAndConditionsResponse() {
        return TermAndConditionsResponse.builder()
                .url(TestConstants.DEFAULT_TC_URL_IOS_VALUE)
                .version(Long.valueOf(TestConstants.DEFAULT_TC_VERSION_IOS_VALUE))
                .build();
    }

    private UrlsResponse getUrlsResponse() {
        return UrlsResponse.builder()
                .urlShare(TestConstants.DEFAULT_URLS_URL_SHARE)
                .urlIpv6Check(TestConstants.DEFAULT_URLS_IPV6_CHECK)
                .controlIpv4Only(TestConstants.DEFAULT_URLS_CONTROL_IPV4_ONLY)
                .openDataPrefix(TestConstants.DEFAULT_URLS_OPEN_DATA_PREFIX)
                .urlMapServer(TestConstants.DEFAULT_URLS_URL_MAP_SERVER)
                .urlIpv4Check(TestConstants.DEFAULT_URLS_URL_IPV4_CHECK)
                .controlIpv6Only(TestConstants.DEFAULT_URLS_CONTROL_IPV6_ONLY)
                .statistics(TestConstants.DEFAULT_URLS_STATISTICS)
                .build();
    }

    private TermAndConditionsResponse getAndroidTermAndConditionsResponse() {
        return TermAndConditionsResponse.builder()
                .url(TestConstants.DEFAULT_TC_URL_ANDROID_VALUE)
                .ndtUrl(TestConstants.DEFAULT_TC_NDT_URL_ANDROID_VALUE)
                .version(Long.valueOf(TestConstants.DEFAULT_TC_VERSION_ANDROID_VALUE))
                .build();
    }

    private MapServerSettingsResponse getMapServerResponse() {
        return MapServerSettingsResponse.builder()
                .host(TestConstants.DEFAULT_MAP_SERVER_HOST)
                .ssl(TestConstants.DEFAULT_FLAG_TRUE)
                .port(TestConstants.DEFAULT_MAP_SERVER_PORT)
                .build();
    }

    private List<MeasurementServerResponseForSettings> getServerResponseList() {
        var testServerResponse = MeasurementServerResponseForSettings.builder()
                .name(TestConstants.DEFAULT_TEST_SERVER_NAME)
                .uuid(TestConstants.DEFAULT_SERVER_UUID)
                .build();
        return List.of(testServerResponse);
    }

    private List<MeasurementServerResponseForSettings> getServerWsResponseList() {
        var testServerResponse = MeasurementServerResponseForSettings.builder()
                .name(TestConstants.DEFAULT_TEST_SERVER_WS_NAME)
                .uuid(TestConstants.DEFAULT_SERVER_WS_UUID)
                .build();
        return List.of(testServerResponse);
    }

    private List<MeasurementServerResponseForSettings> getServerQoSResponseList() {
        var testServerResponse = MeasurementServerResponseForSettings.builder()
                .name(TestConstants.DEFAULT_TEST_SERVER_WS_NAME)
                .uuid(TestConstants.DEFAULT_SERVER_WS_UUID)
                .build();
        return List.of(testServerResponse);
    }


    private List<QosMeasurementTypeDescription> getQoSTestTypeDescResponses() {
        var qosTestTypeDescResponse = QosMeasurementTypeDescription.builder()
                .name(TestConstants.DEFAULT_QOS_TEST_TYPE_DESC_NAME)
                .testType(TestConstants.DEFAULT_TEST_TYPE)
                .build();
        return List.of(qosTestTypeDescResponse);
    }

    private List<Settings> getDefaultSettings() {
        var tcUrlAndroid = new Settings(null, "tc_url_android", TestConstants.DEFAULT_LANGUAGE, TestConstants.DEFAULT_TC_URL_ANDROID_VALUE);
        var tcNdtUrlAndroid = new Settings(null, "tc_ndt_url_android", TestConstants.DEFAULT_LANGUAGE, TestConstants.DEFAULT_TC_NDT_URL_ANDROID_VALUE);
        var tcVersionAndroid = new Settings(null, "tc_version_android", TestConstants.DEFAULT_LANGUAGE, TestConstants.DEFAULT_TC_VERSION_ANDROID_VALUE);
        var tcUrlAndroidV4 = new Settings(null, "tc_url_android_v4", TestConstants.DEFAULT_LANGUAGE, TestConstants.DEFAULT_TC_URL_ANDROID_V4_VALUE);
        var tcUrlIOS = new Settings(null, "tc_url_ios", TestConstants.DEFAULT_LANGUAGE, TestConstants.DEFAULT_TC_URL_IOS_VALUE);
        var tcVersionIOS = new Settings(null, "tc_version_ios", TestConstants.DEFAULT_LANGUAGE, TestConstants.DEFAULT_TC_VERSION_IOS_VALUE);
        var tcVersion = new Settings(null, "tc_version", TestConstants.DEFAULT_LANGUAGE, TestConstants.DEFAULT_TC_VERSION_VALUE);
        var tcUrl = new Settings(null, "tc_url", TestConstants.DEFAULT_LANGUAGE, TestConstants.DEFAULT_TC_URL_VALUE);
        var hostMapServer = new Settings(null, "host_map_server", TestConstants.DEFAULT_LANGUAGE, TestConstants.DEFAULT_MAP_SERVER_HOST);
        var sslMapServer = new Settings(null, "ssl_map_server", TestConstants.DEFAULT_LANGUAGE, String.valueOf(TestConstants.DEFAULT_FLAG_TRUE));
        var portMapServer = new Settings(null, "port_map_server", TestConstants.DEFAULT_LANGUAGE, TestConstants.DEFAULT_MAP_SERVER_PORT.toString());
        var urlOpenDataPrefix = new Settings(null, "url_open_data_prefix", TestConstants.DEFAULT_LANGUAGE, TestConstants.DEFAULT_URLS_OPEN_DATA_PREFIX);
        var urlShare = new Settings(null, "url_share", TestConstants.DEFAULT_LANGUAGE, TestConstants.DEFAULT_URLS_URL_SHARE);
        var urlStatistics = new Settings(null, "url_statistics", TestConstants.DEFAULT_LANGUAGE, TestConstants.DEFAULT_URLS_STATISTICS);
        var controlIpv4Only = new Settings(null, "control_ipv4_only", TestConstants.DEFAULT_LANGUAGE, TestConstants.DEFAULT_URLS_CONTROL_IPV4_ONLY);
        var controlIpv6Only = new Settings(null, "control_ipv6_only", TestConstants.DEFAULT_LANGUAGE, TestConstants.DEFAULT_URLS_CONTROL_IPV6_ONLY);
        var urlIpv4Check = new Settings(null, "url_ipv4_check", TestConstants.DEFAULT_LANGUAGE, TestConstants.DEFAULT_URLS_URL_IPV4_CHECK);
        var urlIpv6Check = new Settings(null, "url_ipv6_check", TestConstants.DEFAULT_LANGUAGE, TestConstants.DEFAULT_URLS_IPV6_CHECK);
        var urlMapServer = new Settings(null, "url_map_server", TestConstants.DEFAULT_LANGUAGE, TestConstants.DEFAULT_URLS_URL_MAP_SERVER);
        return List.of(tcUrlAndroid, tcNdtUrlAndroid, tcVersionAndroid,
                tcUrlAndroidV4, tcUrlIOS, tcVersionIOS,
                tcVersion, tcUrl, hostMapServer,
                sslMapServer, portMapServer, urlOpenDataPrefix, urlShare,
                urlStatistics, controlIpv4Only, controlIpv6Only,
                urlIpv4Check, urlIpv6Check, urlMapServer);
    }
}
