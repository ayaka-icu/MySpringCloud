package com.ganga.hotel.service.impl;

import com.ganga.hotel.constant.MqConstant;
import com.ganga.hotel.mapper.HotelMapper;
import com.ganga.hotel.pojo.Hotel;
import com.ganga.hotel.service.IHotelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.ganga.hotel.constant.MqConstant.HOTEL_EXCHANGE;
import static com.ganga.hotel.constant.MqConstant.HOTEL_INSERT_KEY;

@Service
public class HotelService extends ServiceImpl<HotelMapper, Hotel> implements IHotelService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

}
