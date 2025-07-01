package com.purefunction.ezbt.room.client.agoda;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "agodaClient", url = "http://affiliateapi7643.agoda.com")
public interface AgodaFeignClient {
    // TODO: header 만들어줘야함

//    @PostMapping("/affiliateservice/lt_v1")
//    AgodaResponse searchHotel(
//            @RequestHeader("Authorization") String authorization,
//            @RequestHeader("Accept-Encoding") String acceptEncoding,
//            @RequestBody AgodaRequest criteria
//    );
}