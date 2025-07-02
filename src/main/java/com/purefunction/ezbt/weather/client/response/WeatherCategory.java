package com.purefunction.ezbt.weather.client.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum WeatherCategory {
    // 기온
    TEMPERATURE("T1H"),
    // 습도
    HUMIDITY("REH"),
    // 1시간 강수량
    RAINFALL("RN1"),
    // 하늘 상태 (1: 맑음, 3: 구름많음, 4: 흐림)
    SKY("SKY"),
    // 강수 형태 (0: 없음, 1: 비, 2: 비/눈, 3: 눈, 5: 빗방울, 6: 빗방울/눈날림, 7: 눈날림)
    PRECIPITATION_TYPE("PTY"),
    // 풍속
    WIND_SPEED("WSD"),
    // 알 수 없는 값
    UNKNOWN("UNKNOWN");

    private final String code;

    public static WeatherCategory fromCode(String code) {
        return Arrays.stream(values())
                .filter(category -> category.code.equals(code))
                .findFirst()
                .orElse(UNKNOWN);
    }
}
