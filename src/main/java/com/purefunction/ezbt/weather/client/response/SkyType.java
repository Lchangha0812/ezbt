package com.purefunction.ezbt.weather.client.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum SkyType {
    SUNNY("1", "맑음"),
    CLOUDY("3", "구름많음"),
    OVERCAST("4", "흐림"),
    UNKNOWN("0", "알 수 없음");

    private final String code;
    private final String description;

    public static SkyType fromCode(String code) {
        return Arrays.stream(values())
                .filter(type -> type.code.equals(code))
                .findFirst()
                .orElse(UNKNOWN);
    }
}
