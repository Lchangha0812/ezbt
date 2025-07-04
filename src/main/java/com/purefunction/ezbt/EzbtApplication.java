package com.purefunction.ezbt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableCaching
@SpringBootApplication
@EnableFeignClients
public class EzbtApplication {

    public static void main(String[] args) {
        SpringApplication.run(EzbtApplication.class, args);
    }

}
