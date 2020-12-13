package com.specture.core.service;

import com.specture.core.model.Measurement;
import com.specture.core.model.EnrichedPageWithAggregations;
import com.specture.core.response.BasicTestResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface BasicTestService {
    Page<BasicTestResponse> getBasicTests(Pageable pageable);
    EnrichedPageWithAggregations<BasicTestResponse> getBasicTestsWithFilters(List<Long> packages, List<String> probes, LocalDateTime from, LocalDateTime to, Pageable pageable);

    Page<BasicTestResponse> getWebTests(LocalDateTime from, LocalDateTime to, Pageable pageable);

    Page<BasicTestResponse> getBasicMobileTests(Pageable pageable);
    EnrichedPageWithAggregations<BasicTestResponse> getMobileTestsWithFilters(List<Long> packages, List<String> probes, LocalDateTime from, LocalDateTime to, Pageable pageable);

    Page<BasicTestResponse> getBasicTestsByUuid(Pageable pageable, String uuid);
    void saveMeasurementToElastic(Measurement measurement);
}
