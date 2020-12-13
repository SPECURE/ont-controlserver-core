package com.specture.core.repository;

import com.specture.core.model.BasicTest;
import com.specture.core.model.EnrichedPageWithAggregations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BasicTestRepository {
    Page<BasicTest> findByClientUuid(String clientUuid, Pageable pageable );

    Page<BasicTest> findAll(Pageable pageable);

    String save(BasicTest basicTest);

    Page<BasicTest> findByTypeOfProbePort(String typeofProbe, Pageable page);
    EnrichedPageWithAggregations<BasicTest> findByTypeOfProbePortPackagesAndDate(String typeofProbe, List<Long> packages, List<String> probes, LocalDateTime from, LocalDateTime to, Pageable page);
    Page<BasicTest> findWebByPackagesAndDate(LocalDateTime from, LocalDateTime to, Pageable page);
    long getAmountOfMeasurementsBetween(Timestamp from, Timestamp to);
}
