package com.hust.datn.service.impl;

import com.hust.datn.config.Constants;
import com.hust.datn.service.ProductSearchService;
import com.hust.datn.service.dto.ProductSearchDTO;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.bucket.composite.CompositeAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.composite.CompositeValuesSourceBuilder;
import org.elasticsearch.search.aggregations.bucket.composite.ParsedComposite;
import org.elasticsearch.search.aggregations.bucket.composite.TermsValuesSourceBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.EntityMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProductSearchDTO}.
 */
@Service
public class ProductSearchServiceImpl implements ProductSearchService {

    @Autowired
    EntityMapper entityMapper;

    @Autowired
    private RestHighLevelClient client;


    @Override
    public List<ProductSearchDTO> onSearchObject(String keyword) throws Exception {
        List<ProductSearchDTO> lstObj = new ArrayList<>();

        QueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("name", keyword);

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(matchQueryBuilder);
        sourceBuilder.from(0);
        sourceBuilder.size(100);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        SearchRequest searchRequest = new SearchRequest(Constants.ES.PRODUCT_INDEX);
        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
            ProductSearchDTO obj = entityMapper.mapToObject(searchHit.getSourceAsString(), ProductSearchDTO.class);
            lstObj.add(obj);
        }
        return lstObj;
    }
}
