package com.specture.core.service;

import com.specture.core.model.EnrichedPageWithAggregations;
import com.specture.core.model.qos.MeasurementQos;
import com.specture.core.response.BasicQosTestResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface BasicQosTestService {
    Page<BasicQosTestResponse> getBasicQosTestsFixed(Pageable pageable);
    EnrichedPageWithAggregations<BasicQosTestResponse> getBasicQosTestsWithFilters(List<Long> packages, List<String> probes, LocalDateTime from, LocalDateTime to, Pageable pageable);

    Page<BasicQosTestResponse> getBasicQosTestsMobile(Pageable pageable);
    EnrichedPageWithAggregations<BasicQosTestResponse> getMobileQosTestsWithFilters(List<Long> packages, List<String> probes, LocalDateTime from, LocalDateTime to, Pageable pageable);

    Page<BasicQosTestResponse>getBasicQosTestsByUuid(Pageable pageable, String uuid);
    void saveMeasurementQosToElastic(MeasurementQos measurementQos);
}
