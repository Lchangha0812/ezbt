package com.purefunction.ezbt.weather.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(
        name = "kmaWeatherClient",
        url = "https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0",
        configuration = KmaWeatherConfig.class
)
public interface KmaWeatherFeignClient {

    @GetMapping("/getUltraSrtNcst")
//    List<UltraSrtNcstResponse> getUltraSrtNcst(@RequestParam Map<String, String> queries);
}
