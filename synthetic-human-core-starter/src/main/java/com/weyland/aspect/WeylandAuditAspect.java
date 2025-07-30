package com.weyland.aspect;

import lombok.RequiredArgsConstructor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.kafka.core.KafkaTemplate;

import com.weyland.annotation.WeylandWatchingYou;
import com.weyland.config.WeylandProperties;
import com.weyland.domain.enums.AuditMode;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@RequiredArgsConstructor
public class WeylandAuditAspect {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final WeylandProperties properties;
    

    @Around("@annotation(auditAnnotation)")
    public Object auditMethod(ProceedingJoinPoint joinPoint, WeylandWatchingYou auditAnnotation) throws Throwable {
        String timestamp = LocalDateTime.now().toString();
        String methodName = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        String message = String.format("%s | %s | Args: %s", timestamp, methodName, args);

        switch (auditAnnotation.mode()) {
            case CONSOLE -> System.out.println("[AUDIT] " + message);
            case KAFKA -> sendToKafka(message);
        }
        
        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            String errorMsg = message + " | ERROR: " + e.getMessage();
            System.err.println("[AUDIT-ERROR] " + errorMsg);
            if (auditAnnotation.mode() != AuditMode.CONSOLE) {
                sendToKafka(errorMsg);
            }
            throw e;
        }
    }

    private void sendToKafka(String message) {
        kafkaTemplate.send(properties.getKafka().getTopic(), message);
    }
}