package com.ganga.hotel.controller;

import com.ganga.hotel.pojo.PageResult;
import com.ganga.hotel.pojo.RequestParams;
import com.ganga.hotel.service.impl.HotelService;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping("/list")
    public PageResult queryList(@RequestBody RequestParams requestParams) {

        return hotelService.searchList(requestParams);
    }
}
