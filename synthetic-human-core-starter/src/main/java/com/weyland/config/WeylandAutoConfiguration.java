package com.weyland.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.kafka.core.KafkaTemplate;

import com.weyland.aspect.WeylandAuditAspect;
import com.weyland.service.contract.CommandExecutor;
import com.weyland.service.contract.CommandInfrastructureService;
import com.weyland.service.contract.MetricService;
import com.weyland.service.impl.CommandExecutorImpl;
import com.weyland.service.impl.CommandInfrastructureServiceImpl;
import com.weyland.service.impl.MetricServiceImpl;

import io.micrometer.core.instrument.MeterRegistry;

@Configuration
@EnableAspectJAutoProxy
@EnableConfigurationProperties(WeylandProperties.class)
@ConditionalOnClass(KafkaTemplate.class)
public class WeylandAutoConfiguration {

    @Bean
    public WeylandAuditAspect weylandAuditAspect(
        KafkaTemplate<String, String> kafkaTemplate, 
        WeylandProperties properties
    ) {
        return new WeylandAuditAspect(kafkaTemplate, properties);
    }

    @Bean
    @ConditionalOnMissingBean
    public CommandExecutor commandExecutor() {
        return new CommandExecutorImpl(); 
    }
    
    @Bean
    @ConditionalOnMissingBean
    public CommandInfrastructureService commandInfrastructureService(
        CommandExecutor commandExecutor,
        MetricService metricService,
        MeterRegistry meterRegistry,
        WeylandProperties properties
    ) {
        return new CommandInfrastructureServiceImpl(
            commandExecutor,
            metricService,
            meterRegistry
        );
    }
    
    @Bean
    @ConditionalOnMissingBean
    public MetricService metricService(MeterRegistry meterRegistry) {
        return new MetricServiceImpl(meterRegistry);
    }
}