package com.ganga.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(value = "storage-service")
public interface StorageClient {

    @PutMapping("storage/{code}/{count}")
    void deduct(@PathVariable("code") String code, @PathVariable("count") Integer count);

}
