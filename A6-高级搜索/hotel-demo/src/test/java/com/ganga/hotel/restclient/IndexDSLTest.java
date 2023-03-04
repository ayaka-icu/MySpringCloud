package com.ganga.hotel.restclient;

import com.ganga.hotel.HotelDemoApplication;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static com.ganga.hotel.constant.ESConstant.CREATE_INDEX_HOTEL;

@SpringBootTest(classes = HotelDemoApplication.class)
public class IndexDSLTest {

    private RestHighLevelClient restClient;

    @BeforeEach //创建 RestClient
    public void initRest() {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("elastic", "password"));
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

    @AfterEach //清除客户端
    public void closeRest() throws IOException {
        restClient.close();
    }

    /**
     * 测试初始化
     */
    @Test
    public void initTest(){
        System.out.println(restClient);
    }

    /**
     * 添加索引库
     * @throws IOException
     */
    @Test
    public void createIndexTest() throws IOException {
        //1.设置Request对象，声明请求方式和路径 : GET /hotel
        CreateIndexRequest request = new CreateIndexRequest("hotel");
        //2.设置请求参数，也就是DSL语句。
        request.source(CREATE_INDEX_HOTEL, XContentType.JSON);
        //3.发起请求
        restClient.indices().create(request, RequestOptions.DEFAULT);
    }

    /**
     * 删除索引
     * @throws IOException
     */
    @Test
    public void deleteIndexTest() throws IOException {
        //1.设置Request对象，声明请求方式和路径 : DELETE /hotel
        DeleteIndexRequest request = new DeleteIndexRequest("hotel");
        //2.设置请求参数，也就是DSL语句。 无参数
        //3.发起请求
        restClient.indices().delete(request, RequestOptions.DEFAULT);
    }

    /**
     * 查询索引是否存在
     * @throws IOException
     */
    @Test
    public void getIndexTest() throws IOException {
        //1.设置Request对象，声明请求方式和路径 : GET /hotel
        GetIndexRequest request = new GetIndexRequest("hotel");
        //2.设置请求参数，也就是DSL语句。 无参数
        //3.发起请求
        boolean exists = restClient.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

}
