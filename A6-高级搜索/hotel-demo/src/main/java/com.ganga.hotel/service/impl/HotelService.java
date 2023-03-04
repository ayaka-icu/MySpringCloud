package com.ganga.hotel.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ganga.hotel.mapper.HotelMapper;
import com.ganga.hotel.pojo.Hotel;
import com.ganga.hotel.pojo.HotelDoc;
import com.ganga.hotel.pojo.PageResult;
import com.ganga.hotel.pojo.RequestParams;
import com.ganga.hotel.service.IHotelService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Service
public class HotelService extends ServiceImpl<HotelMapper, Hotel> implements IHotelService {

    @Autowired
    private RestHighLevelClient restClient;

    @Override
    public PageResult searchList(RequestParams requestParams) {

        try {
            // 解析参数
            String key = requestParams.getKey();
            int page = requestParams.getPage();
            int size = requestParams.getSize();
            // 创建请求
            SearchRequest request = new SearchRequest("hotel");
            SearchSourceBuilder source = request.source();
            // 布尔查询 - 原始查询
            BoolQueryBuilder boolQuery = new BoolQueryBuilder();
            if (ObjectUtils.isEmpty(key)) {
                // 无搜索内容
                boolQuery.must(QueryBuilders.matchAllQuery());
            } else {
                // 有搜索内容
                boolQuery.must(QueryBuilders.matchQuery("all", key));
            }
            // 条件搜索 brand 精确查询
            if (ObjectUtils.isNotEmpty(requestParams.getBrand())) {
                boolQuery.filter(QueryBuilders.termQuery("brand", requestParams.getBrand()));
            }
            // 条件搜索 city 精确查询
            if (ObjectUtils.isNotEmpty(requestParams.getCity())) {
                boolQuery.filter(QueryBuilders.termQuery("city", requestParams.getCity()));
            }
            // 条件搜索 starName 精确查询
            if (ObjectUtils.isNotEmpty(requestParams.getStarName())) {
                boolQuery.filter(QueryBuilders.termQuery("starName", requestParams.getStarName()));
            }
            // 条件查询 min/maxPrice 范围查询
            if (ObjectUtils.isNotEmpty(requestParams.getMinPrice()) && ObjectUtils.isNotEmpty(requestParams.getMaxPrice())) {
                boolQuery.filter(QueryBuilders.rangeQuery("price")
                        .gte(requestParams.getMinPrice())
                        .lte(requestParams.getMaxPrice()));
            }
            // 排序 算分函数 广告优先
            FunctionScoreQueryBuilder functionScore = QueryBuilders.functionScoreQuery(
                    // 原始查询，相关性算分的查询
                    boolQuery,
                    // function score的数组
                    new FunctionScoreQueryBuilder.FilterFunctionBuilder[]{
                            // 其中的一个function score 元素
                            new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                                    // 过滤条件
                                    QueryBuilders.termQuery("isAD", true),
                                    // 算分函数
                                    ScoreFunctionBuilders.weightFactorFunction(10)
                            )
                    });
            // source 内的 query 内的 functionScoreQuery 内的 boolQuery
            // 原始查询boolQuery放入functionScore functionScore放入query query放入source
            source.query(functionScore);


            // 排序 位置
            if (ObjectUtils.isNotEmpty(requestParams.getLocation())) {
                source.sort(SortBuilders.geoDistanceSort("location", new GeoPoint(requestParams.getLocation()))
                        .order(SortOrder.ASC)
                        .unit(DistanceUnit.KILOMETERS)
                );
            }
            // 分页
            source.from((page - 1) * size).size(size);
            // 高亮
            source.highlighter(new HighlightBuilder().field("name").requireFieldMatch(false));
            // 发送请求
            SearchResponse response = restClient.search(request, RequestOptions.DEFAULT);
            // 请求结果处理
            return this.handleResponse(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 解析 封装数据
     *
     * @param response es查询全部数据
     * @return PageResult 数据
     */
    private PageResult handleResponse(SearchResponse response) {
        // 解析查询条数
        long total = response.getHits().getTotalHits().value;
        // 解析酒店数据
        ArrayList<HotelDoc> hotels = new ArrayList<>();
        for (SearchHit hit : response.getHits().getHits()) {
            String json = hit.getSourceAsString();
            HotelDoc hotel = JSON.parseObject(json, HotelDoc.class);
            //解析排序字段 --> 距离
            Object[] sorts = hit.getSortValues();
            if (ObjectUtils.isNotEmpty(sorts)) {
                Object sort = sorts[0];
                hotel.setDistance(sort);
            }
            //高亮
            Map<String, HighlightField> highlights = hit.getHighlightFields();
            if (ObjectUtils.isNotEmpty(highlights)) {
                HighlightField field = highlights.get("name");
                if (ObjectUtils.isNotEmpty(field)) {
                    hotel.setName(field.getFragments()[0].string());
                }
            }
            //添加到集合
            hotels.add(hotel);
        }
        // 封装 返回
        return new PageResult(total, hotels);
    }

}
