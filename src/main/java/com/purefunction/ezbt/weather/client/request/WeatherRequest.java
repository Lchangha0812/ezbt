package com.purefunction.ezbt.weather.client.request;

import lombok.Builder;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class WeatherRequest {
    @ToString.Exclude
    private final String serviceKey;

    private final int numOfRows;
    private final int pageNo;
    private final String dataType;
    private final String baseDate;
    private final String baseTime;
    private final int nx;
    private final int ny;

    @Builder
    private WeatherRequest(String serviceKey, int numOfRows, int pageNo, int nx, int ny, LocalDate baseDate, LocalDateTime baseTime) {
        this.serviceKey = serviceKey;
        this.numOfRows = numOfRows;
        this.pageNo = pageNo;
        this.dataType = "JSON";
        this.nx = nx;
        this.ny = ny;
        this.baseDate = baseDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        this.baseTime = baseTime.format(DateTimeFormatter.ofPattern("HHmm"));
    }

    public Map<String, String> toQueryMap() {
        Map<String, String> map = new HashMap<>();
        map.put("serviceKey", serviceKey);
        map.put("numOfRows", String.valueOf(numOfRows));
        map.put("pageNo", String.valueOf(pageNo));
        map.put("dataType", dataType);
        map.put("base_date", baseDate);
        map.put("base_time", baseTime);
        map.put("nx", String.valueOf(nx));
        map.put("ny", String.valueOf(ny));
        return map;
    }
}
