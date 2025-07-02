package com.purefunction.ezbt.weather.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WeatherResponse(
        @JsonProperty("baseDate") String baseDate,
        @JsonProperty("baseTime") String baseTime,
        @JsonProperty("category") String category,
        @JsonProperty("nx") int nx,
        @JsonProperty("ny") int ny,
        @JsonProperty("obsrValue") String obsrValue, // 초단기실황
        @JsonProperty("fcstValue") String fcstValue  // 초단기예보, 단기예보
) {
}
