package com.ganga.hotel.constant;

public class MqConstant {

    /**
     * 交换机
     */
    public static final String HOTEL_EXCHANGE = "hotel.exchange";

    /**
     * 消息队列
     */
    public static final String HOTEL_INSERT_QUEUE = "hotel.insert.queue";
    public static final String HOTEL_DELETE_QUEUE = "hotel.delete.queue";


    /**
     * 新增 或 修改 的 RoutingKey
     */
    public static final String HOTEL_INSERT_KEY = "hotel.insert";
    public static final String HOTEL_DELETE_KEY = "hotel.delete";
}
