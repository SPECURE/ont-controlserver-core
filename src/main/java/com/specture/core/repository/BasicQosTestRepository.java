package com.specture.core.repository;

import com.specture.core.model.BasicQosTest;
import com.specture.core.model.EnrichedPageWithAggregations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface BasicQosTestRepository {
    Page<BasicQosTest> findByTypeOfProbePort(String typeofProbe, Pageable page);

    String save(BasicQosTest basicTest);
    EnrichedPageWithAggregations<BasicQosTest> findByTypeOfProbePortPackagesAndDate(String typeofProbe, List<Long> packages, List<String> probe, LocalDateTime from, LocalDateTime to, Pageable page);
    Page<BasicQosTest> findByClientUuid(String uuid, Pageable page);
}
