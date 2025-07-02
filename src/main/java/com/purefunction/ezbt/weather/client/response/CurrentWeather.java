package com.purefunction.ezbt.weather.client.response;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record CurrentWeather(
        Double temperature,
        Double humidity,
        Double rainfall,
        Double windSpeed,
        SkyType skyType,
        PrecipitationType precipitationType
) {
    public static CurrentWeather from(List<WeatherResponse> responses) {
        Map<WeatherCategory, String> weatherData = responses.stream()
                .collect(Collectors.toMap(response -> WeatherCategory.fromCode(response.category()), WeatherResponse::obsrValue));

        return new CurrentWeather(
                Double.parseDouble(weatherData.getOrDefault(WeatherCategory.TEMPERATURE, "0.0")),
                Double.parseDouble(weatherData.getOrDefault(WeatherCategory.HUMIDITY, "0.0")),
                Double.parseDouble(weatherData.getOrDefault(WeatherCategory.RAINFALL, "0.0")),
                Double.parseDouble(weatherData.getOrDefault(WeatherCategory.WIND_SPEED, "0.0")),
                SkyType.fromCode(weatherData.getOrDefault(WeatherCategory.SKY, "0")),
                PrecipitationType.fromCode(weatherData.getOrDefault(WeatherCategory.PRECIPITATION_TYPE, "0"))
        );
    }
}
