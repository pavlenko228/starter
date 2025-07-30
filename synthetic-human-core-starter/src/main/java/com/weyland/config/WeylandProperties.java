package com.weyland.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "weyland")
@Getter 
@Setter 
public class WeylandProperties {
    private final Kafka kafka = new Kafka();
    private final Queue queue = new Queue();

    @Getter
    @Setter
    public static class Kafka {
        private String topic = "android-audit-log";
        private boolean enabled = true;
    }

    @Getter
    @Setter
    public static class Queue {
        private int maxSize = 1000;
    }
}