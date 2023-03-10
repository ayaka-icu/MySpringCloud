package com.ganga.order.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class SentinelConfig implements RequestOriginParser {

    /**
     * 获取请头，用于授权验证
     * @param httpServletRequest request
     * @return 控制应用 -> 以头参数进行识别
     */
    @Override
    public String parseOrigin(HttpServletRequest httpServletRequest) {
        //这里是获取的 请求头 也可用用其他的
        String origin = httpServletRequest.getHeader("origin");
        if (StringUtils.isEmpty(origin)){
            return "no-origin-param";
        }
        return origin;
    }




}
