package com.weyland;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.weyland.config.WeylandAutoConfiguration;

@SpringBootApplication
@Import(WeylandAutoConfiguration.class)
public class HumanCoreStarterApplication {
    public static void main(String[] args) {
        SpringApplication.run(HumanCoreStarterApplication.class, args);
    }
}
