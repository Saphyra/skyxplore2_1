package com.github.saphyra.skyxplore.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class RequestConstants {
    public static final String COOKIE_CHARACTER_ID = "characterid";
    public static final String COOKIE_USER_ID = "userid";

    public static final List<String> PROPERTY_PATHS = Arrays.asList(
        "/**/favicon.ico",
        "/css/**",
        "/images/**",
        "/js/**",
        "/i18n/**"
    );
}
