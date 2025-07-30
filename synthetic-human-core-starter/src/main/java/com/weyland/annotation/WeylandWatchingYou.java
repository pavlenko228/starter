package com.weyland.annotation;

import java.lang.annotation.*;

import com.weyland.domain.enums.AuditMode;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface WeylandWatchingYou {
    AuditMode mode() default AuditMode.CONSOLE;
}


