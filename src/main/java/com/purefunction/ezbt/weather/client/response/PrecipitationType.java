package com.purefunction.ezbt.weather.client.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum PrecipitationType {
    NONE("0", "없음"),
    RAIN("1", "비"),
    RAIN_SNOW("2", "비/눈"),
    SNOW("3", "눈"),
    SHOWER("4", "소나기"),
    RAINDROP("5", "빗방울"),
    RAINDROP_SNOWDRIFT("6", "빗방울/눈날림"),
    SNOWDRIFT("7", "눈날림"),
    UNKNOWN("99", "알 수 없음");

    private final String code;
    private final String description;

    public static PrecipitationType fromCode(String code) {
        return Arrays.stream(values())
                .filter(type -> type.code.equals(code))
                .findFirst()
                .orElse(UNKNOWN);
    }
}
