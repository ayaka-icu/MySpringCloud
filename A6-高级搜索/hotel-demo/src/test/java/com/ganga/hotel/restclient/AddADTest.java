package com.ganga.hotel.restclient;

import com.ganga.hotel.HotelDemoApplication;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static com.ganga.hotel.constant.ESConstant.*;

@SpringBootTest(classes = HotelDemoApplication.class)
public class AddADTest {

    @Autowired
    private RestHighLevelClient restClient;

    /**
     * 给几个hotel添加广告
     * @throws IOException
     */
    @Test
    public void addADTest() throws IOException {
        BulkRequest request = new BulkRequest("hotel");
        request.add(new UpdateRequest("hotel",ADD_AD_TEST_01_ID).doc("isAD",true));
        request.add(new UpdateRequest("hotel",ADD_AD_TEST_02_ID).doc("isAD",true));
        request.add(new UpdateRequest("hotel",ADD_AD_TEST_03_ID).doc("isAD",true));
        restClient.bulk(request, RequestOptions.DEFAULT);
    }
}
