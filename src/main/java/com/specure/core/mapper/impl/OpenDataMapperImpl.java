package com.specure.core.mapper.impl;

import com.specure.core.enums.NetworkType;
import com.specure.core.mapper.OpenDataMapper;
import com.specure.core.model.OpenData;
import com.specure.core.model.OpenDataExport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.text.SimpleDateFormat;

import static com.specure.core.service.impl.OpenDataServiceImpl.CSV_SEPARATOR;

@Slf4j
@Component
@AllArgsConstructor
public class OpenDataMapperImpl implements OpenDataMapper {

    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private static final String NA = "N/A";
    private static final String IPV4 = "ipv4";
    private static final String IPV6 = "ipv6";

    @Override
    public OpenDataExport openDataToOpenDataExport(OpenData openData) {
        return OpenDataExport.builder()
                .openTestUuid(openData.getOpenTestUuid() != null && !openData.getOpenTestUuid().isEmpty() ? openData.getOpenTestUuid() : NA)
                .time(openData.getTime() != null ? DATE_FORMATTER.format(openData.getTime()) : NA)
                .speedDownload(openData.getSpeedDownload() != null ? openData.getSpeedDownload().toString() : NA)
                .speedUpload(openData.getSpeedUpload() != null ? openData.getSpeedUpload().toString() : NA)
                .pingMedian(openData.getPingMedian() != null ? String.valueOf((float) openData.getPingMedian() / 1000000) : NA)
                .signalStrength(openData.getSignalStrength() != null && openData.getSignalStrength() < 0 ? openData.getSignalStrength().toString() : NA)
                .lteRsrp(openData.getLte_rsrp() != null && openData.getLte_rsrp() < 0 ? openData.getLte_rsrp().toString() : NA)
                .platform(openData.getPlatform() != null ? openData.getPlatform().name() : NA)
                .provider(getProvider(openData.getNetworkOperator(), openData.getClientProvider()))
                .numThreadsDl(openData.getTestNumThreads() != null ? openData.getTestNumThreads().toString() : NA)
                .numThreadsUl(openData.getNumThreadsUl() != null ? openData.getNumThreadsUl().toString() : NA)
                .networkType(openData.getNetworkType() != null ? NetworkType.fromValue(openData.getNetworkType()).getName() : NA)
                .catTechnology(openData.getNetworkType() != null ? NetworkType.fromValue(openData.getNetworkType()).getCategory() : NA)
                .ipProtocol(getIpProtocol(openData.getIpAddress()))
                .build();
    }

    private String getProvider(String networkOperator, String clientProvider) {
        if (networkOperator != null && !networkOperator.isEmpty()) {
            return networkOperator.replace(CSV_SEPARATOR, ' ');
        } else if (clientProvider != null && !clientProvider.isEmpty()) {
            return clientProvider.replace(CSV_SEPARATOR, ' ');
        } else {
            return NA;
        }
    }

    private String getIpProtocol(String ipAddress) {
        try {
            InetAddress inetAddress = InetAddress.getByName(ipAddress);
            if (inetAddress instanceof Inet4Address) {
                return IPV4;
            } else if (inetAddress instanceof Inet6Address) {
                return IPV6;
            } else {
                return NA;
            }
        } catch (Exception e) {
            log.error("An error occurred when getting info about ip protocol! {}", e.getMessage());
        }
        return NA;
    }
}
