package com.ganga.hotel.restclient;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.ganga.hotel.pojo.HotelDoc;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

@SpringBootTest
public class SearchDSLTest {

    private RestHighLevelClient restClient;

    /**
     * match_all 查询所有
     *
     * @throws IOException
     */
    @Test
    public void matchAllQueryTest() throws IOException {
        //1.准备request请求
        SearchRequest request = new SearchRequest("hotel");
        //2.准备DSL语句
        request.source().query(QueryBuilders.matchAllQuery());
        //3.发起请求
        SearchResponse response = restClient.search(request, RequestOptions.DEFAULT);

        //解析相应结果
        SearchHits hits = response.getHits();
        long total = hits.getTotalHits().value;
        System.out.println("共搜索到" + total + "条数据");
        //解析数据
        for (SearchHit hit : hits.getHits()) {
            String json = hit.getSourceAsString();
            //反序列化
            HotelDoc hotelDoc = JSON.parseObject(json, HotelDoc.class);
            System.out.println(hotelDoc + "\n");
        }
    }


    /**
     * 检索查询
     *
     * @throws IOException
     */
    @Test
    public void matchQueryTest() throws IOException {
        //发起请求
        SearchRequest request = new SearchRequest("hotel");
        request.source().query(
                QueryBuilders.matchQuery("all", "如家")
        );
        SearchResponse response = restClient.search(request, RequestOptions.DEFAULT);

        //解析结果
        handleResponse(response);

    }

    /**
     * 复合查询 (布尔查询)
     * 精确查询 (term、range)
     *
     * @throws IOException
     */
    @Test
    public void boolQueryTest() throws IOException {
        SearchRequest request = new SearchRequest("hotel");
        request.source().query(
                //先构建一个 boolQuery() 布尔查询
                QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("name", "和颐")) //match 检索查询
                        .must(QueryBuilders.rangeQuery("price").gte(100).lte(300)) //range 数值查询
                        .mustNot(QueryBuilders.termQuery("starName", "一钻")) //term 精确查询
                        .mustNot(QueryBuilders.termQuery("starName", "二钻"))
                        //.must(QueryBuilders.)    // 必须匹配
                        //.should(QueryBuilders.)  // 选择性匹配
                        //.mustNot(QueryBuilders.) // 必须不匹配 不参与算分
                        //.filter(QueryBuilders.)  // 必须匹配   不参与算分

        );
        SearchResponse response = restClient.search(request, RequestOptions.DEFAULT);
        // 解析结果
        handleResponse(response);
    }


    /**
     * 分页查询
     * 排序查询
     * @throws IOException
     */
    @Test
    public void OrderPageQueryTest() throws IOException {
        // 接收分页参数
        int page = 2, size = 5; // 查第二页 每页5条
        // 创建 request
        SearchRequest request = new SearchRequest("hotel");
        // source 相当于 json数据的 { }  query,from,size,...都在其内部
        SearchSourceBuilder source = request.source();
        // 查询条件
        source.query(QueryBuilders.matchQuery("all", "如家"));
        // 分页
        source.from((page - 1) * size).size(size); // 以后 page 会变得 第一页就为 0
        // 排序
        source.sort("price", SortOrder.ASC);
        // 发送
        SearchResponse response = restClient.search(request, RequestOptions.DEFAULT);
        // 结果处理
        handleResponse(response);
    }

    /**
     * 高亮查询
     * 高亮数据解析 this.handleResponse(response)
     * @throws IOException
     */
    @Test
    public void addHighlightQueryTest() throws IOException {
        // 接收分页参数
        int page = 2, size = 5; // 查第二页 每页5条
        // 创建 request
        SearchRequest request = new SearchRequest("hotel");
        // source 相当于 json数据的 { }  query,from,size,...都在其内部
        SearchSourceBuilder source = request.source();
        // 查询条件
        source.query(QueryBuilders.matchQuery("all", "如家"));
        // 分页
        source.from((page - 1) * size).size(size); // 以后 page 会变得 第一页就为 0
        // 排序
        source.sort("price", SortOrder.ASC);
        // 高亮
        source.highlighter(
                new HighlightBuilder() // 这是要 new 一个 HighlightBuilder()
                        .field("name")      // field 指定高亮的字段
                        .requireFieldMatch(false) // false 对非搜索字段高亮，默认为 true 不对非搜索字段高亮
        );
        // 发送
        SearchResponse response = restClient.search(request, RequestOptions.DEFAULT);
        // 结果处理
        handleResponse(response);
        // 为了实现高亮 还需要对结果解析进行 继续 处理
    }


    /**
     * 解析结果
     *
     * @param response
     */
    private static void handleResponse(SearchResponse response) {
        //解析结果
        long total = response.getHits().getTotalHits().value;
        System.out.println("共搜索到" + total + "条数据");
        for (SearchHit hit : response.getHits().getHits()) {
            // 获取查询数据
            String json = hit.getSourceAsString();
            HotelDoc hotelDoc = JSON.parseObject(json, HotelDoc.class);
            // 获取高亮数据
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            if (!CollectionUtils.isEmpty(highlightFields)){
                // 获取高亮字段
                HighlightField field = highlightFields.get("name");
                if (field != null){
                    String name = field.getFragments()[0].string();
                    //替换原有数据
                    hotelDoc.setName(name);
                }
            }
            System.out.println(hotelDoc + "\n");
        }
    }

    //===================================================================================

    @BeforeEach
    void init() {
        // Http 地址端口 及 用户认证
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                AuthScope.ANY,
                new UsernamePasswordCredentials("elastic", "password"));
        //构建RestHighLevelClient对象
        this.restClient = new RestHighLevelClient(
                RestClient.builder(
                                new HttpHost("ayaka520", 9200, "http"))
                        .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                                httpClientBuilder.disableAuthCaching();
                                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                            }
                        })
        );
    }

    @AfterEach
    void close() throws IOException {
        this.restClient.close();
    }

}
