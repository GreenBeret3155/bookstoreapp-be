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
import java.util.Arrays;
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

//    public Page<ProductSearchDTO> onSearch(ProductSearchDTO reportKpiDTO, Pageable pageable) throws Exception {
//        List<ProductSearchDTO> productSearchDTO = new ArrayList<>();
//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        QueryBuilder queryBuilder = QueryBuilders.boolQuery();
//        List<String> listKpi = reportKpiDTO.getKpiIds().stream().map(TreeValue::getId).collect(Collectors.toList());
//        ((BoolQueryBuilder) queryBuilder).must(QueryBuilders.termQuery("discount_rate", 35).lte(4).gte(4.8));
//        if(!DataUtil.isNullOrEmpty(reportKpiDTO.getInputLevels())) {
//            ((BoolQueryBuilder) queryBuilder).mus;
//        }
//        if (!DataUtil.isNullOrEmpty(reportKpiDTO.getObjects())) {
//            ((BoolQueryBuilder) queryBuilder).filter(QueryBuilders.termsQuery("objCodeFull.keyword", reportKpiDTO.getObjects()));
//        }
//        ((BoolQueryBuilder) queryBuilder).filter(QueryBuilders.termQuery(Constants.BASE_RPT_GRAPH.TIME_TYPE, reportKpiDTO.getTimeType()));
//
//        RangeQueryBuilder rangQuery = null;
//        if (Constants.VALUE_TYPE.VAL.equalsIgnoreCase(reportKpiDTO.getValueType())) {
//            rangQuery = QueryBuilders.rangeQuery("val");
//        } else if (Constants.VALUE_TYPE.VAL_ACC.equalsIgnoreCase(reportKpiDTO.getValueType())) {
//            rangQuery = QueryBuilders.rangeQuery("valAcc");
//        } else if (Constants.VALUE_TYPE.VAL_TOTAL.equalsIgnoreCase(reportKpiDTO.getValueType())) {
//            rangQuery = QueryBuilders.rangeQuery("valTotal");
//        }
//        if (!DataUtil.isNullOrEmpty(reportKpiDTO.getFromValue()) && rangQuery != null) {
//            rangQuery.from(reportKpiDTO.getFromValue());
//        }
//        if (!DataUtil.isNullOrEmpty(reportKpiDTO.getToValue())  && rangQuery != null) {
//            rangQuery.to(reportKpiDTO.getToValue());
//        }
//        if (!DataUtil.isNullOrEmpty(reportKpiDTO.getFromValue()) || !DataUtil.isNullOrEmpty(reportKpiDTO.getToValue())) {
//            ((BoolQueryBuilder) queryBuilder).filter(rangQuery);
//        }
//
//        if (reportKpiDTO.getFromDate() != null || reportKpiDTO.getToDate() != null) {
//            RangeQueryBuilder prdIdRange = QueryBuilders.rangeQuery("prdId");
//            if (reportKpiDTO.getFromDate() != null) {
//                prdIdRange.from(DataUtil.getDateInt(reportKpiDTO.getFromDate(), Constants.DATE_FORMAT_YYYYMMDD));
//            }
//            if (reportKpiDTO.getToDate() != null) {
//                prdIdRange.to(DataUtil.getDateInt(reportKpiDTO.getToDate(), Constants.DATE_FORMAT_YYYYMMDD));
//            }
//            ((BoolQueryBuilder) queryBuilder).filter(prdIdRange);
//        }
//
//        sourceBuilder.query(queryBuilder);
//        if (pageable != null) {
//            sourceBuilder.from(Long.valueOf(pageable.getOffset()).intValue());
//            sourceBuilder.size(pageable.getPageSize());
//        }
//        sourceBuilder.sort(Constants.BASE_RPT_GRAPH.PRD_ID, SortOrder.DESC);
//        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
//
//        SearchRequest searchRequest = new SearchRequest(Constants.ES.BASE_RPT_GRAPH);
//        searchRequest.source(sourceBuilder);
//        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
//        SearchHit[] result = searchResponse.getHits().getHits();
//        long total = searchResponse.getHits().getTotalHits();
//        for (SearchHit searchHit : result) {
//            ProductSearchDTO baseRpt = entityMapper.mapToObject(searchHit.getSourceAsString(), ProductSearchDTO.class);
//            productSearchDTO.add(baseRpt);
//        }
//        return new PageImpl<>(productSearchDTO, pageable, total);
//    }
}
