package com.ganga.gateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

//当order值一样时，
//顺序是 defaultFilter > 局部的路由过滤器 > 全局过滤器
@Component
//@Order(-1) //设置过滤器顺序
public class AuthorizeFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1.获取请求参数
        MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();
        //2.获取 authorization
        String auth = queryParams.getFirst("auth"); // authorization
        //3.校验
        if("admin".equals(auth)){
            //校验成功
            return chain.filter(exchange);
        }
        //校验失败
        //设置状态码
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        //拦截
        return exchange.getResponse().setComplete();
    }

    //order值越小，优先级越高，执行顺序越靠前
    @Override
    public int getOrder() {
        return -1;
    }
}
