package com.specure.core.model;

import com.specure.core.model.buckets.PackageBucket;
import com.specure.core.model.buckets.PortBucket;
import com.specure.core.model.buckets.ProbeBucket;
import com.specure.core.model.buckets.StatusBucket;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.function.Function;

@Getter
@Setter
public class EnrichedPageWithAggregations<T> extends PageImpl<T> {
    private List<PackageBucket> packages;
    private List<ProbeBucket> probes;
    private List<PortBucket> ports;
    private List<StatusBucket> statuses;

    public EnrichedPageWithAggregations(Page<T> page) {
        super(page.getContent(), page.getPageable(), page.getTotalElements());
    }

    public <U> EnrichedPageWithAggregations<U> mapEnriched(Function<T,U> converter){
        var simplePageLogs = super.map(converter);
        EnrichedPageWithAggregations<U> result = new EnrichedPageWithAggregations<>(simplePageLogs);
        result.setPackages(this.getPackages());
        result.setProbes(this.getProbes());
        result.setPorts(this.getPorts());
        result.setStatuses(this.getStatuses());
        return result;
    }
}
