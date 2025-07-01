package com.purefunction.ezbt.weather.client;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

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

                JavaType javaType = objectMapper.constructType(type);

                return objectMapper.readValue(itemsNode.traverse(), javaType);
            }
        }
    }
}
