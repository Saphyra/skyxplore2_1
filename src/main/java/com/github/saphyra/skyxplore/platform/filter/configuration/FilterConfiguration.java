package com.github.saphyra.skyxplore.platform.filter.configuration;

import com.github.saphyra.skyxplore.common.PageController;
import com.github.saphyra.skyxplore.common.RequestConstants;
import com.github.saphyra.skyxplore.platform.filter.CharacterIdCleanupFilter;
import com.github.saphyra.skyxplore.platform.filter.HeaderSetupFilter;
import com.github.saphyra.skyxplore.platform.filter.LocaleFilter;
import com.github.saphyra.skyxplore.platform.filter.UserIdCleanupFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class FilterConfiguration {
    private static final int CHARACTER_ID_CLEANUP_FILTER_ORDER = 1;
    private static final int HEADER_SETUP_FILTER_ORDER = Integer.MIN_VALUE;
    private static final int LOCALE_FILTER_ORDER = 20;
    private static final int USER_ID_CLEANUP_FILTER_ORDER = 10;

    @Bean
    public FilterRegistrationBean<UserIdCleanupFilter> userIdCleanupFilterFilterRegistrationBean(
        UserIdCleanupFilter userIdCleanupFilter
    ) {
        log.info("UserIdCleanupFilter order: {}", USER_ID_CLEANUP_FILTER_ORDER);
        FilterRegistrationBean<UserIdCleanupFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(userIdCleanupFilter);
        filterRegistrationBean.setOrder(USER_ID_CLEANUP_FILTER_ORDER);
        filterRegistrationBean.addUrlPatterns(PageController.INDEX_MAPPING);
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<LocaleFilter> localeFilterFilterRegistrationBean(
        LocaleFilter localeFilter
    ) {
        log.info("LocaleFilter order: {}", LOCALE_FILTER_ORDER);
        FilterRegistrationBean<LocaleFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(localeFilter);
        filterRegistrationBean.setOrder(LOCALE_FILTER_ORDER);
        filterRegistrationBean.addUrlPatterns(
            RequestConstants.WEB_PREFIX + "/*",
            RequestConstants.API_PREFIX + "/*"
        );
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<CharacterIdCleanupFilter> characterIdCleanupFilterFilterRegistrationBean(
        CharacterIdCleanupFilter characterIdCleanupFilter
    ) {
        log.info("CharacterIdCleanupFilter order: {}", CHARACTER_ID_CLEANUP_FILTER_ORDER);
        FilterRegistrationBean<CharacterIdCleanupFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(characterIdCleanupFilter);
        filterRegistrationBean.setOrder(CHARACTER_ID_CLEANUP_FILTER_ORDER);
        filterRegistrationBean.addUrlPatterns(RequestConstants.WEB_PREFIX + "/*");
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<HeaderSetupFilter> headerSetupFilterFilterRegistrationBean(
        HeaderSetupFilter headerSetupFilter
    ) {
        log.info("HeaderSetupFilter order: {}", HEADER_SETUP_FILTER_ORDER);
        FilterRegistrationBean<HeaderSetupFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(headerSetupFilter);
        filterRegistrationBean.setOrder(HEADER_SETUP_FILTER_ORDER);
        filterRegistrationBean.addUrlPatterns(RequestConstants.WEB_PREFIX + "/*", RequestConstants.WEB_PREFIX);
        return filterRegistrationBean;
    }
}
