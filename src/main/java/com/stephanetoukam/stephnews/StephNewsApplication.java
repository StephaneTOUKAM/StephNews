package com.stephanetoukam.stephnews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * <pre>
 *     com.stephanetoukam.stephnews.StephNewsApplication
 * </pre>
 *
 * @author Stephane TOUKAM <me@stephanetoukam.com>
 * 3 Nov 2023 16:23
 */
@SpringBootApplication
@EnableAsync
public class StephNewsApplication {

    public static void main(String[] args) {
        SpringApplication.run(StephNewsApplication.class, args);
    }

}
