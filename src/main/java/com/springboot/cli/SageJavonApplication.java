package com.springboot.cli;

import com.springboot.cli.common.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class SageJavonApplication {

    public static void main(String[] args) {
        SpringApplication.run(SageJavonApplication.class, args);
    }

}
