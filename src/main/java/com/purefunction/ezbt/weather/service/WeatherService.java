package com.purefunction.ezbt.weather.service;

import com.purefunction.ezbt.weather.client.KmaWeatherFeignClient;
import com.purefunction.ezbt.weather.client.request.WeatherRequest;
import com.purefunction.ezbt.weather.client.response.CurrentWeather;
import com.purefunction.ezbt.weather.client.response.ForecastWeather;
import com.purefunction.ezbt.weather.util.CoordinateConverter;
import com.purefunction.ezbt.weather.util.Point;
import com.purefunction.ezbt.weather.util.TimeConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final KmaWeatherFeignClient feignClient;
    @Value("${kma.service-key}")
    private String serviceKey;



    @Cacheable(value = "forecastWeather", key = "#lat + '-' + #lon")
    public ForecastWeather getForecastWeather(double lat, double lon) {
        Point gridPoint = CoordinateConverter.convertLatLngToGrid(lat, lon);
        LocalDateTime baseTime = TimeConverter.getVilageFcstBaseTime(LocalDateTime.now());

        WeatherRequest request = WeatherRequest.builder()
                .serviceKey(serviceKey)
                .nx(gridPoint.getX())
                .ny(gridPoint.getY())
                .baseDate(baseTime.toLocalDate())
                .baseTime(baseTime)
                .build();

        return feignClient.getVilageFcst(request.toQueryMap());
    }
}

