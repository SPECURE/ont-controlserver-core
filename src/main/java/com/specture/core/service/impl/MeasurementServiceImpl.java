package com.specture.core.service.impl;

import com.specture.core.config.MeasurementServerConfig;
import com.specture.core.constant.ErrorMessage;
import com.specture.core.constant.MeasurementServerConstants;
import com.specture.core.enums.MeasurementStatus;
import com.specture.core.enums.Platform;
import com.specture.core.enums.ServerType;
import com.specture.core.exception.MeasurementNotFoundByUuidException;
import com.specture.core.mapper.MeasurementMapper;
import com.specture.core.model.Measurement;
import com.specture.core.model.MeasurementServer;
import com.specture.core.model.TimeSlot;
import com.specture.core.model.internal.DataForMeasurementRegistration;
import com.specture.core.repository.BasicTestRepository;
import com.specture.core.repository.MeasurementRepository;
import com.specture.core.request.MeasurementRequest;
import com.specture.core.response.MeasurementHistoryResponse;
import com.specture.core.response.MeasurementRegistrationResponse;
import com.specture.core.response.MeasurementStatsForGeneralUserPortalResponse;
import com.specture.core.service.MeasurementService;
import com.specture.core.service.UserAgentExtractService;
import com.specture.core.service.digger.DiggerService;
import com.specture.core.utils.HeaderExtrudeUtil;
import com.specture.core.utils.MeasurementCalculatorUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.time.*;
import java.util.*;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.firstDayOfYear;

@Slf4j
@RequiredArgsConstructor
@Service
public class MeasurementServiceImpl implements MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final MeasurementMapper measurementMapper;
    private final MeasurementServerConfig measurementServerConfig;
    private final DiggerService diggerService;
    private final UserAgentExtractService userAgentExtractService;
    private final BasicTestRepository basicTestRepository;
    private Clock clock = Clock.systemUTC(); //todo to bean

    @Override
    public void setClock(Clock clock) {
        this.clock = clock;
    }

    @Override
    @Deprecated
    public void saveMeasurement(MeasurementRequest measurementRequest) {
        Measurement measurement = measurementMapper.measurementRequestToMeasurement(measurementRequest);
        measurement.setStatus(MeasurementStatus.FINISHED);
        measurementRepository.save(measurement);
    }

    @Override
    public Measurement partialUpdateMeasurementFromProbeResult(MeasurementRequest measurementRequest) {
        Measurement afterMeasure = measurementMapper.measurementRequestToMeasurement(measurementRequest);
        var registeredMeasurement = measurementRepository
                .findByToken(afterMeasure.getToken())
                .orElseThrow(()-> new MeasurementNotFoundByUuidException(afterMeasure.getToken()));

        Long id = registeredMeasurement.getId();

        afterMeasure.setId(id);
        afterMeasure.setOpenTestUuid(registeredMeasurement.getOpenTestUuid());
        afterMeasure.setClientUuid(registeredMeasurement.getClientUuid());
        afterMeasure.setNetworkOperator(registeredMeasurement.getNetworkOperator());
        afterMeasure.setDevice(registeredMeasurement.getDevice());
        afterMeasure.setTag(registeredMeasurement.getTag());
        afterMeasure.setIpAddress(registeredMeasurement.getIpAddress());
        afterMeasure.setToken(registeredMeasurement.getToken());
        afterMeasure.setTestSlot(registeredMeasurement.getTestSlot());
        afterMeasure.setTime(registeredMeasurement.getTime());
        afterMeasure.setMeasurementServerId(registeredMeasurement.getMeasurementServerId());
        afterMeasure.setServerType(registeredMeasurement.getServerType());
        afterMeasure.setClientProvider(registeredMeasurement.getClientProvider());
        afterMeasure.setStatus(MeasurementStatus.FINISHED);
        afterMeasure.setAdHocCampaign(registeredMeasurement.getAdHocCampaign());

        if(afterMeasure.getPings() != null) {
            afterMeasure.getPings().forEach( ping -> ping.setMeasurement(afterMeasure));
        }
        if(afterMeasure.getGeoLocations() != null) {
            afterMeasure.getGeoLocations().forEach( geoLocation -> geoLocation.setMeasurement(afterMeasure));
        }
        if(afterMeasure.getSpeedDetail() != null) {
            afterMeasure.getSpeedDetail().forEach( speedDetail -> speedDetail.setMeasurement(afterMeasure));
        }
        measurementRepository.save(afterMeasure);
        return afterMeasure;
    }

    @Override
    public MeasurementRegistrationResponse registerMeasurement(DataForMeasurementRegistration dataForMeasurementRegistration, Map<String, String> headers) {
        MeasurementServer measurementServer = dataForMeasurementRegistration.getMeasurementServer();
        String ip = HeaderExtrudeUtil.getIpFromNgNixHeader(headers);
        String clientProviderName = ErrorMessage.UNKNOWN_PROVIDER;

        try {
            Long asn = diggerService.digASN(InetAddress.getByName(ip));
            if (asn != 0L) {
                clientProviderName = diggerService.getProviderByASN(asn);
            }
        } catch (UnknownHostException e){
            log.error("asn detection error");
        }

        String providerFromPackage = null;
        if (dataForMeasurementRegistration.getProvider() != null) {
            providerFromPackage = dataForMeasurementRegistration.getProvider().getName();
        }
        TimeSlot timeSlot = getTimeSlot(Instant.now().toEpochMilli());
        String testUuid = MeasurementCalculatorUtil.getUuid();
        String testToken = MeasurementCalculatorUtil.getToken(measurementServer.getSecretKey(), testUuid, timeSlot.getSlot());
        String device = dataForMeasurementRegistration.getDeviceOrProbeId();
        if (device == null) {
            String userAgentHeader =  HeaderExtrudeUtil.getUserAgent(headers);
            device = userAgentExtractService.getBrowser(userAgentHeader);
        }

        Measurement measurement = Measurement.builder()
                .openTestUuid(testUuid)
                .clientUuid(dataForMeasurementRegistration.getClientUuid())
                .adHocCampaign(dataForMeasurementRegistration.getAdHocCampaign())
                .measurementServerId(measurementServer.getId())
                .networkOperator(providerFromPackage)
                .clientProvider(clientProviderName)
                .device(device)
                .tag(dataForMeasurementRegistration.getPort())
                .token(testToken)
                .testSlot(timeSlot.getSlot())
                .time(new Timestamp(System.currentTimeMillis()))
                .ipAddress(ip)
                .status(MeasurementStatus.STARTED)
                .platform(Platform.UNKNOWN)
                .build();

        if(dataForMeasurementRegistration.getIsOnNet() != null) {
            ServerType serverType = dataForMeasurementRegistration.getIsOnNet() ? ServerType.ON_NET : ServerType.OFF_NET;
            measurement.setServerType(serverType.toString());
        }

        Measurement savedMeasurement = measurementRepository.save(measurement);
        Integer port = dataForMeasurementRegistration.getClientType().getServerTechForMeasurement().getDefaultSslPort();

        return MeasurementRegistrationResponse.builder()
                .testNumThreads(MeasurementServerConstants.TEST_NUM_THREADS)
                .testNumPings(Integer.valueOf(MeasurementServerConstants.TEST_NUM_PINGS))
                .testDuration(5)
                .testId(savedMeasurement.getId())
                .testWait(timeSlot.getTestWait())
                .testUuid(testUuid)
                .testToken(testToken)
                .testServerPort(port)
                .testServerEncryption(true)
                .resultUrl(String.format(MeasurementServerConstants.RESULT_URL, measurementServerConfig.getHost()))
                .resultQosUrl(String.format(MeasurementServerConstants.RESULT_QOS_URL, measurementServerConfig.getHost()))
                .testServerName(measurementServer.getName())
                .testServerAddress(measurementServer.getWebAddress())
                .clientRemoteIp(ip)
                .provider(clientProviderName)
                .error(Collections.emptyList())
                .build();
    }

    public TimeSlot getTimeSlot(long now) {

        int slot = Math.toIntExact( now / 1000);
        long counter = measurementRepository.countAllByTestSlot(slot);
        int wait = 0;
        long max = measurementServerConfig.getSlotWindow().intValue();

        while (counter >= max){
            slot++;
            wait++;
            counter = measurementRepository.countAllByTestSlot(slot);
        }

        return TimeSlot.builder()
                .slot(slot)
                .testWait(wait)
                .build();
    }

    @Override
    public List<Measurement> findLastMeasurementsForMeasurementServerIds(List<Long> ids) {
        return measurementRepository.findLastMeasurementsForMeasurementServerIds(ids);
    }

    @Override
    public MeasurementHistoryResponse getMeasurementDetailByUuid(String uuid) {
        return measurementRepository
                .findByOpenTestUuid(uuid)
                .map(measurementMapper::measurementToMeasurementHistoryResponse)
                .orElseThrow(() -> new MeasurementNotFoundByUuidException(uuid));

    }

    @Override
    public Measurement findByOpenTestUuid(String uuid) {
        return measurementRepository.findByOpenTestUuid(uuid).orElseThrow(() -> new MeasurementNotFoundByUuidException(uuid));
    }

    @Override
    public MeasurementStatsForGeneralUserPortalResponse getMeasurementStatsForGeneralUserPortalResponse(ZoneId zoneId, DayOfWeek dayWhenWeekStarts) {
        Timestamp from;

        // for Timestamp constructor default timezone, important !!
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        ZoneId utcZoneId = ZoneId.of("UTC");
        log.info("start measurement start with zoneId={}; dayWhenWeekStart={}", zoneId, dayWhenWeekStarts);

        ZonedDateTime zonedDateTimeNow = ZonedDateTime.of(LocalDateTime.now(this.clock), utcZoneId);
        ZonedDateTime userNow = zonedDateTimeNow.withZoneSameInstant(zoneId);
        Timestamp now = Timestamp.valueOf(zonedDateTimeNow.toLocalDateTime());
        log.info("now={}", now);

        LocalDate nowDate = userNow.toLocalDate();
        ZonedDateTime userStartDay = ZonedDateTime.of(nowDate.atStartOfDay(), zoneId);
        ZonedDateTime utcStartDay = userStartDay.withZoneSameInstant(utcZoneId);
        from = Timestamp.valueOf(utcStartDay.toLocalDateTime());
        log.info("DAY from={}", from.toInstant());

        long thisDay = basicTestRepository.getAmountOfMeasurementsBetween(from, now );

        DayOfWeek todayDayOfWeek = LocalDateTime.now(this.clock).getDayOfWeek();
        int delta = todayDayOfWeek.getValue() - dayWhenWeekStarts.getValue();
        if (delta < 0) {
            delta += 7;
        }
        LocalDate dayStartWeek = utcStartDay.toLocalDate().minusDays(delta);
        LocalTime timeStartWeek = utcStartDay.toLocalTime();
        from = Timestamp.valueOf(LocalDateTime.of(dayStartWeek, timeStartWeek));
        log.info("WEEK from={}", from.toInstant());
        long thisWeek = basicTestRepository.getAmountOfMeasurementsBetween(from, now);

        LocalDateTime startMonthForUser = userNow.toLocalDate().with(firstDayOfMonth()).atStartOfDay();
        ZonedDateTime dayStartMonthInUserZone = ZonedDateTime.of(startMonthForUser, zoneId);
        ZonedDateTime startMonthDateTimeUTC = dayStartMonthInUserZone.withZoneSameInstant(utcZoneId);
        from = Timestamp.valueOf(startMonthDateTimeUTC.toLocalDateTime());
        log.info("MONTH from={}", from.toInstant());
        long thisMonth = basicTestRepository.getAmountOfMeasurementsBetween(from, now);

        LocalDateTime dayStartYearForUser = userNow.toLocalDate().with(firstDayOfYear()).atStartOfDay();
        ZonedDateTime dayStartYearInUserZone = ZonedDateTime.of(dayStartYearForUser, zoneId);
        ZonedDateTime startYearUTC = dayStartYearInUserZone.withZoneSameInstant(utcZoneId);
        from = Timestamp.valueOf(startYearUTC.toLocalDateTime());
        log.info("YEAR from={}", from.toInstant());
        long thisYear = basicTestRepository.getAmountOfMeasurementsBetween(from, now);

        return MeasurementStatsForGeneralUserPortalResponse.builder()
                .today(thisDay)
                .thisWeek(thisWeek)
                .thisMonth(thisMonth)
                .thisYear(thisYear)
                .build();
    }

    @Override
    public Optional<Measurement> getMeasurementByToken(String token) {
        return measurementRepository.findByToken(token);
    }

    @Override
    public List<Measurement> getLastSuccessfulMeasurementByIds(List<Long> ids) {
        return measurementRepository.findTopByOrderByStatusAndMeasurementServerIdOrderByTime(ids);
    }

}

