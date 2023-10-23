package com.stephanetoukam.stephnews;

import com.stephanetoukam.stephnews.config.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableConfigurationProperties(StorageProperties.class)
public class StephNewsApplication {

    public static void main(String[] args) {
        SpringApplication.run(StephNewsApplication.class, args);
    }

}
