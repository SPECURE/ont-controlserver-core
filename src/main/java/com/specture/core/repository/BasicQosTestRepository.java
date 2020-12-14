package com.specture.core.repository;

import com.specture.core.model.BasicQosTest;
import com.specture.core.model.EnrichedPageWithAggregations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BasicQosTestRepository extends ElasticsearchRepository<BasicQosTest, String> {
    Page<BasicQosTest> findByTypeOfProbePort(String typeofProbe, Pageable page);
    EnrichedPageWithAggregations<BasicQosTest> findByTypeOfProbePortPackagesAndDate(String typeofProbe, List<Long> packages, List<String> probe, LocalDateTime from, LocalDateTime to, Pageable page);
    Page<BasicQosTest> findByClientUuid(String uuid, Pageable page);
}
