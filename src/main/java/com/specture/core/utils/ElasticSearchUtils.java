package com.specture.core.utils;

import com.specture.core.model.buckets.PackageBucket;
import com.specture.core.model.buckets.PortBucket;
import com.specture.core.model.buckets.ProbeBucket;
import com.specture.core.model.buckets.StatusBucket;
import lombok.experimental.UtilityClass;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;

@UtilityClass
public class ElasticSearchUtils {
    public Stream<? extends Terms.Bucket> extrudeStringAggregationFrom(SearchResponse searchResponse, String aggName) {
        final Optional<ParsedStringTerms> parsedStringTerms = getAggregation(searchResponse, aggName)
                .map(ParsedStringTerms.class::cast);
        return extrudeAggregation(parsedStringTerms.orElseThrow());

    }
    public Stream<? extends Terms.Bucket> extrudeLongAggregationFrom(SearchResponse searchResponse, String aggName) {
        final Optional<ParsedLongTerms> parsedLongTerms = getAggregation(searchResponse, aggName)
                .map(ParsedLongTerms.class::cast);
        return extrudeAggregation(parsedLongTerms.orElseThrow());

    }
    private Optional<Aggregation> getAggregation(SearchResponse searchResponse, String aggName) {
        return Optional.of(searchResponse)
                .map(SearchResponse::getAggregations)
                .map(Aggregations::asMap)
                .map(a -> a.get(aggName));
    }
    private Stream<? extends Terms.Bucket>extrudeAggregation(ParsedTerms terms) {
        return Optional.of(terms)
                .map(ParsedTerms::getBuckets)
                .map(Collection::stream)
                .orElseGet(Stream::empty);
    }

    public List<PackageBucket> getPackageIdBucketListAsString(SearchResponse searchResponse, String aggName) {
        if(Objects.isNull(searchResponse)) {
            return Collections.emptyList();
        }
        return ElasticSearchUtils.extrudeStringAggregationFrom(searchResponse, aggName)
                .map(data -> PackageBucket.builder()
                        .docCounter(data.getDocCount())
                        .packageId(Long.valueOf((String) data.getKey()))
                        .build()
                )
                .collect(Collectors.toList());
    }
    public List<PackageBucket> getPackageIdBucketListAsLong(SearchResponse searchResponse, String aggName) {
        if(Objects.isNull(searchResponse)) {
            return Collections.emptyList();
        }
        return ElasticSearchUtils.extrudeLongAggregationFrom(searchResponse, aggName)
                .map(data -> PackageBucket.builder()
                        .docCounter(data.getDocCount())
                        .packageId((Long) data.getKey())
                        .build()
                )
                .collect(Collectors.toList());
    }
    public List<ProbeBucket> getProbeBucketList(SearchResponse searchResponse, String aggName) {
        if(Objects.isNull(searchResponse)) {
            return Collections.emptyList();
        }
        return ElasticSearchUtils
                .extrudeStringAggregationFrom(searchResponse, aggName)
                .map(data -> ProbeBucket.builder()
                        .docCounter(data.getDocCount())
                        .probe((String) data.getKey())
                        .build()
                )
                .collect(Collectors.toList());
    }
    public List<PortBucket> getPortBucketList(SearchResponse searchResponse, String aggName) {
        if(Objects.isNull(searchResponse)) {
            return Collections.emptyList();
        }
        return ElasticSearchUtils
                .extrudeStringAggregationFrom(searchResponse, aggName)
                .map(data -> PortBucket.builder()
                        .docCounter(data.getDocCount())
                        .port((String) data.getKey())
                        .build()
                )
                .collect(Collectors.toList());
    }
    public List<StatusBucket> getStatusBucketList(SearchResponse searchResponse, String aggName) {
        if(Objects.isNull(searchResponse)) {
            return Collections.emptyList();
        }
        return ElasticSearchUtils
                .extrudeStringAggregationFrom(searchResponse, aggName)
                .map(data -> StatusBucket.builder()
                        .docCounter(data.getDocCount())
                        .status((String) data.getKey())
                        .build()
                )
                .collect(Collectors.toList());
    }
    public static Optional<BoolQueryBuilder> getFilterByStringField(String termField, List<String> data) {
        if (Objects.isNull(data) || data.size() < 1) {
            return Optional.empty();
        }
        BoolQueryBuilder filteredByQuery = boolQuery();
        data.forEach(one -> filteredByQuery.should(QueryBuilders.termsQuery(termField, one)));
        return Optional.of(filteredByQuery);
    }
    public static Optional<BoolQueryBuilder> getFilterByLongFieldList(String termField, List<Long> data) {
        if (Objects.isNull(data) || data.size() < 1) {
            return Optional.empty();
        }
        BoolQueryBuilder filteredByQuery = boolQuery();
        data.forEach(one -> filteredByQuery.should(QueryBuilders.termsQuery(termField, one.toString())));
        return Optional.of(filteredByQuery);
    }

    public static Optional<RangeQueryBuilder> getTimeFilter(String field, LocalDateTime from, LocalDateTime to) {
        if(Objects.isNull(from) || Objects.isNull(to)) {
            return Optional.empty();
        }
        return Optional.of(QueryBuilders.rangeQuery(field).from(from).to(to));
    }

    public static Pageable keywordTransform(Pageable pageable, Map<String, String> fields) {
        var sortOrderIterator = pageable.getSort().stream().iterator();
        List<Sort.Order> listOfSort = new ArrayList<>();

        while (sortOrderIterator.hasNext()){
            var order = sortOrderIterator.next();
            var property = order.getProperty();
            if (fields.containsKey(property)) {
                Sort.Order updatedOrder = new Sort.Order(order.getDirection(), fields.get(property));
                listOfSort.add(updatedOrder);
            } else {
                listOfSort.add(order);
            }
        }
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(listOfSort));
    }
}
