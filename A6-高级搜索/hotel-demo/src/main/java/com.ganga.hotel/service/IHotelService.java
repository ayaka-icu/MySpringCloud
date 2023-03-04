package com.ganga.hotel.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ganga.hotel.pojo.Hotel;
import com.ganga.hotel.pojo.PageResult;
import com.ganga.hotel.pojo.RequestParams;

import java.io.IOException;

public interface IHotelService extends IService<Hotel> {
    PageResult searchList(RequestParams requestParams);

}
