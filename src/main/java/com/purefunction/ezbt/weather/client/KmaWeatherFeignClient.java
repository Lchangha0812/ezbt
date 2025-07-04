package com.purefunction.ezbt.weather.client;

import com.purefunction.ezbt.weather.client.response.CurrentWeather;
import com.purefunction.ezbt.weather.client.response.ForecastWeather;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@FeignClient(
        name = "kma-weather",
        url = "https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0",
        configuration = KmaWeatherConfig.class
)
public interface KmaWeatherFeignClient {

    @GetMapping(value = "/getUltraSrtNcst")
    CurrentWeather getUltraSrtNcst(@QueryMap Map<String, String> queries);

    @GetMapping(value = "/getVilageFcst")
    ForecastWeather getVilageFcst(@QueryMap Map<String, String> queries);
}
