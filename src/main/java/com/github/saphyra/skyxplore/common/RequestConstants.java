package com.github.saphyra.skyxplore.common;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RequestConstants {
    public static final String DEFAULT_LOCALE = "hu";
    public static final String COOKIE_CHARACTER_ID = "characterid";
    public static final String COOKIE_LOCALE = "locale";
    public static final String COOKIE_USER_ID = "userid";

    public static final String WEB_PREFIX = "/web";
    public static final String API_PREFIX = "/api";

    public static final List<String> CHARACTER_SELECT_REQUIRED_URIS = Arrays.asList(
        PageController.COMMUNITY_MAPPING,
        PageController.EQUIPMENT_MAPPING,
        PageController.FACTORY_MAPPING,
        PageController.HANGAR_MAPPING,
        PageController.OVERVIEW_MAPPING,
        PageController.SHOP_MAPPING
    );

    public static final List<String> PROPERTY_PATHS = Arrays.asList(
        "/**/favicon.ico",
        "/css/**",
        "/images/**",
        "/js/**",
        "/i18n/**"
    );
}
