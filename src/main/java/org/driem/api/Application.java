package org.driem.api;

import io.bitsensor.plugins.java.spring.annotation.EnableBitSensor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(basePackages = "org.driem.api",
        excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern="org.driem.api.elastic.*"))
@EnableBitSensor
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        System.out.println("Done");
    }
}
