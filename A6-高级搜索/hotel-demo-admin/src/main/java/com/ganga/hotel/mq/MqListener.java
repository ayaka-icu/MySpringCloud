package com.ganga.hotel.mq;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.ganga.hotel.constant.MqConstant;
import com.ganga.hotel.pojo.Hotel;
import com.ganga.hotel.pojo.HotelDoc;
import com.ganga.hotel.service.impl.HotelService;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MqListener {

    @Autowired
    private HotelService hotelService;
    @Autowired
    private RestHighLevelClient restClient;

    @RabbitListener(queues = MqConstant.HOTEL_INSERT_QUEUE)
    public void insertOrUpdateQueue(Long id) {
        //根据队列中的 id 查询数据库
        Hotel hotel = hotelService.getById(id);
        HotelDoc hotelDoc = new HotelDoc(hotel);
        System.err.println(JSON.toJSONString(hotelDoc));
        try {
            //更新 ES 文档
            IndexRequest request = new IndexRequest("hotel").id(id.toString());
            request.source(JSON.toJSONString(hotelDoc), XContentType.JSON);
            restClient.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @RabbitListener(queues = MqConstant.HOTEL_DELETE_QUEUE)
    public void deleteQueue(String id) {
        System.err.println(id);
        try {
            // 根据 id 删除 ES 中的文档
            DeleteRequest request = new DeleteRequest("hotel").id(id);
            restClient.delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
