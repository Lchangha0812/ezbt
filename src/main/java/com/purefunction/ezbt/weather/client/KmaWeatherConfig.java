package com.purefunction.ezbt.weather.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.purefunction.ezbt.weather.client.response.CurrentWeather;
import com.purefunction.ezbt.weather.client.response.ForecastWeather;
import com.purefunction.ezbt.weather.client.response.WeatherResponse;
import feign.Response;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class KmaWeatherConfig {

    private final ObjectMapper objectMapper;

    @Bean
    public Decoder feignDecoder() {
        return new CustomDecoder(objectMapper);
    }

    @Bean
    public ErrorDecoder feignErrorDecoder() {
        return new ErrorDecoder.Default();
    }

    public static class CustomDecoder implements Decoder {

        private final ObjectMapper objectMapper;

        public CustomDecoder(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Override
        public Object decode(Response response, Type type) throws IOException {
            try (InputStream bodyIs = response.body().asInputStream()) {
                JsonNode rootNode = objectMapper.readTree(bodyIs);
                JsonNode itemsNode = rootNode.path("response").path("body").path("items").path("item");

                CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, WeatherResponse.class);
                List<WeatherResponse> weatherResponses = objectMapper.readValue(itemsNode.traverse(), listType);

                if (type.equals(CurrentWeather.class)) {
                    return CurrentWeather.from(weatherResponses);
                } else if (type.equals(ForecastWeather.class)) {
                    return ForecastWeather.from(weatherResponses);
                }

                return weatherResponses; // Fallback for other types
            }
        }
    }
}
