package com.ganga.hotel.restclient;


import com.alibaba.fastjson.JSON;
import com.ganga.hotel.HotelDemoApplication;
import com.ganga.hotel.pojo.Hotel;
import com.ganga.hotel.pojo.HotelDoc;
import com.ganga.hotel.service.impl.HotelService;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest(classes = HotelDemoApplication.class)
public class DocumentDSLTest {

    private RestHighLevelClient restClient;

    @Autowired
    private HotelService hotelService;

    private final Long id = 197488318L;

    /**
     * 根据 id 添加文档
     *
     * @throws IOException
     */
    @Test
    public void addDocumentTest() throws IOException {
        //1.从数据库中查询店铺
        Hotel hotel = hotelService.getById(id);
        //2.转换为 HotelDoc 类型
        HotelDoc hotelDoc = new HotelDoc(hotel);
        //3.序列化为 JSON
        String jsonString = JSON.toJSONString(hotelDoc);

        //1.创建request对象
        IndexRequest request = new IndexRequest("hotel").id(id.toString());//注意要加上.id()
        //2.准备DSL语句
        request.source(jsonString, XContentType.JSON);
        //3.发起请求
        restClient.index(request, RequestOptions.DEFAULT);
    }

    /**
     * 根据 id 查询文档
     *
     * @throws IOException
     */
    @Test
    public void getDocumentById() throws IOException {
        //1.创建request对象
        GetRequest request = new GetRequest("hotel").id(id.toString());
        //2.准备DSL语句
        //request.id("197488318");
        //3.发起请求
        GetResponse response = restClient.get(request, RequestOptions.DEFAULT);
        //4.反序列化
        String json = response.getSourceAsString();
        HotelDoc hotelDoc = JSON.parseObject(json, HotelDoc.class);
        System.out.println(hotelDoc);
    }

    /**
     * 根据id 修改文档
     *
     * @throws IOException
     */
    @Test
    public void updateDocumentById() throws IOException {
        //这是增量修改！！！
        restClient.update( //坑: 两个参数 一个是 index 一个是id 而不是链式..... 坑:
                new UpdateRequest("hotel", id.toString()).doc(
                        "name", "神里绫华--第二字段",
                        "address", "神里绫华--第二字段"
                ),
                RequestOptions.DEFAULT
        );
    }


    /**
     * 根据 Id 删除文档
     *
     * @throws IOException
     */
    @Test
    public void deleteDocumentById() throws IOException {
        restClient.delete(
                new DeleteRequest("hotel").id(id.toString()),
                RequestOptions.DEFAULT
        );
    }

    /**
     * 根据id 批量导入文档
     */
    @Test
    public void bulkAddDocById() throws IOException {
        //从 MySql中获取所有数据
        List<Hotel> hotels = hotelService.list();
        //创建 BulkRequest对象
        BulkRequest request = new BulkRequest("hotel");
        hotels.forEach(hotel -> {
            //转换为 HotelDoc 并 序列化为 JSON
            String json = JSON.toJSONString(new HotelDoc(hotel));
            //使用 add() 方法添加 单个 request方法  //坑：别忘了加 .id()
            request.add(new IndexRequest("hotel").id(hotel.getId().toString()).source(json, XContentType.JSON));
        });
        //这里使用 restClient.bulk()
        restClient.bulk(request, RequestOptions.DEFAULT);
    }




    //=======================================================

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
