package com.ganga.hotel.web;

import com.ganga.hotel.pojo.Hotel;
import com.ganga.hotel.pojo.PageResult;
import com.ganga.hotel.service.IHotelService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;

import static com.ganga.hotel.constant.MqConstant.*;

@RestController
@RequestMapping("hotel")
public class HotelController {

    @Autowired
    private IHotelService hotelService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/{id}")
    public Hotel queryById(@PathVariable("id") Long id) {
        return hotelService.getById(id);
    }

    @GetMapping("/list")
    public PageResult hotelList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "1") Integer size
    ) {
        Page<Hotel> result = hotelService.page(new Page<>(page, size));

        return new PageResult(result.getTotal(), result.getRecords());
    }

    /**
     * 新增
     *
     * @param hotel
     */
    @PostMapping
    public void saveHotel(@RequestBody Hotel hotel) {

        //数据库新增数据
        boolean isSuccess = hotelService.save(hotel);
        System.out.println(hotel.getId());
        //发送消息 更新索引
        if (isSuccess) {
            // HOTEL_EXCHANGE 交换机, KEY , 数据 -> 为了减小消息体积 只发个id就行
            rabbitTemplate.convertAndSend(HOTEL_EXCHANGE, HOTEL_INSERT_KEY, hotel.getId());
        }
    }

    /**
     * 修改
     *
     * @param hotel
     */
    @PutMapping()
    public void updateById(@RequestBody Hotel hotel) {
        if (hotel.getId() == null) {
            throw new InvalidParameterException("id不能为空");
        }
        boolean isSuccess = hotelService.updateById(hotel);
        if (isSuccess) {
            // 发送消息
            rabbitTemplate.convertAndSend(HOTEL_EXCHANGE, HOTEL_INSERT_KEY, hotel.getId());
        }
    }

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        boolean isSuccess = hotelService.removeById(id);
        if (isSuccess) {
            rabbitTemplate.convertAndSend(HOTEL_EXCHANGE, HOTEL_DELETE_KEY, id);
        }
    }
}
