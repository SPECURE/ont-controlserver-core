import com.specture.core.model.BasicQosTest;
import com.specture.core.model.EnrichedPageWithAggregations;
import com.specture.core.multitenant.MultiTenantManager;
import com.specture.core.repository.BasicQosTestRepository;
import com.specture.core.utils.ElasticSearchUtils;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;

@Repository
@RequiredArgsConstructor
public class BasicQosTestRepositoryImpl implements BasicQosTestRepository {

    private static final String TERM_QUERY_FILTER_FIELD_CLIENT_UUID = "clientUuid.keyword";
    private static final String TERM_QUERY_FILTER_FIELD_PROBE_PORT_TYPE = "typeOfProbePort.keyword";
    private static final String DATE_FIELD = "measurementDate";
    private static final String TERMS_FIELD = "packageId.keyword";

    private static final String AD_HOC_CAMPAIGN_FIELD = "adHocCampaign";

    private static final String TERMS_PACKAGE_FIELD = "packageId.keyword";
    private static final String TERMS_PROBE_FIELD = "probeId.keyword";

    private static final String PACKAGE_AGG = "package";
    private static final String PROBE_AGG = "probe";

    private static final String DOCUMENT_NAME = "basic_qos_test";

    private final ElasticsearchOperations elasticsearchOperations;
    private final MultiTenantManager multiTenantManager;

    @Override
    public Page<BasicQosTest> findByTypeOfProbePort(String typeofProbe, Pageable page) {
        BoolQueryBuilder filteredQuery = boolQuery();
        filteredQuery.must(QueryBuilders.termQuery(TERM_QUERY_FILTER_FIELD_PROBE_PORT_TYPE, typeofProbe));
        filteredQuery.mustNot(QueryBuilders.existsQuery(AD_HOC_CAMPAIGN_FIELD));

        return getFromElasticSearchPageQuery(filteredQuery, page);
    }

    @Override
    public EnrichedPageWithAggregations<BasicQosTest> findByTypeOfProbePortPackagesAndDate(String typeofProbe, List<Long> packages, List<String> probes, LocalDateTime from, LocalDateTime to, Pageable page) {

        BoolQueryBuilder filteredQuery = boolQuery();

        ElasticSearchUtils.getTimeFilter(DATE_FIELD, from, to).ifPresent(filteredQuery::must);
        ElasticSearchUtils.getFilterByLongFieldList(TERMS_PACKAGE_FIELD, packages).ifPresent(filteredQuery::must);
        ElasticSearchUtils.getFilterByStringField(TERMS_PROBE_FIELD, probes).ifPresent(filteredQuery::must);

        TermQueryBuilder filterByProbePortType = QueryBuilders.termQuery(TERM_QUERY_FILTER_FIELD_PROBE_PORT_TYPE, typeofProbe);
        filteredQuery.must(filterByProbePortType);
        filteredQuery.mustNot(QueryBuilders.existsQuery(AD_HOC_CAMPAIGN_FIELD));

        final SearchQuery searchQuery = new NativeSearchQueryBuilder()
            .withQuery(filteredQuery)
            .withSearchType(SearchType.DEFAULT)
            .withIndices(multiTenantManager.getCurrentTenantQosIndex())
            .withTypes(DOCUMENT_NAME)
            .addAggregation(terms(PACKAGE_AGG).field(TERMS_PACKAGE_FIELD).size(50))
            .addAggregation(terms(PROBE_AGG).field(TERMS_PROBE_FIELD).size(50))
            .withPageable(page)
            .build();

        SearchResponse searchResponse = elasticsearchOperations.query(searchQuery, q -> q);
        var measurements = elasticsearchOperations.queryForPage(searchQuery, BasicQosTest.class);
        var result = new EnrichedPageWithAggregations<>(measurements);
        result.setPackages(ElasticSearchUtils.getPackageIdBucketListAsString(searchResponse, PACKAGE_AGG));
        result.setProbes(ElasticSearchUtils.getProbeBucketList(searchResponse, PROBE_AGG));
        return result;
    }

    @Override
    public Page<BasicQosTest> findByClientUuid(String clientUuid, Pageable pageable) {
        BoolQueryBuilder filteredQuery = boolQuery();
        filteredQuery.must(QueryBuilders.termQuery(TERM_QUERY_FILTER_FIELD_CLIENT_UUID, clientUuid));
        final SearchQuery searchQuery = new NativeSearchQueryBuilder()
            .withQuery(filteredQuery)
            .withSearchType(SearchType.DEFAULT)
            .withIndices(multiTenantManager.getCurrentTenantQosIndex())
            .withTypes("basic_qos_test")
            .withPageable(pageable)
            .build();
        return elasticsearchOperations.queryForPage(searchQuery, BasicQosTest.class);
    }

    @Override
    public String save(BasicQosTest basicTest) {
        IndexQuery indexQuery = new IndexQueryBuilder()
            .withIndexName(multiTenantManager.getCurrentTenantQosIndex())
            .withType("basic_qos_test")
            .withObject(basicTest)
            .build();
        return elasticsearchOperations.index(indexQuery);
    }

    private Page<BasicQosTest> getFromElasticSearchPageQuery(BoolQueryBuilder filteredQuery, Pageable page) {
        final SearchQuery searchQuery = new NativeSearchQueryBuilder()
            .withQuery(filteredQuery)
            .withSearchType(SearchType.DEFAULT)
            .withIndices(multiTenantManager.getCurrentTenantQosIndex())
            .withTypes(DOCUMENT_NAME)
            .withPageable(page)
            .build();
        return elasticsearchOperations.queryForPage(searchQuery, BasicQosTest.class);
    }

}
