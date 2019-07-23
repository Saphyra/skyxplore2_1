package com.github.saphyra.skyxplore.filter.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.saphyra.skyxplore.common.RequestConstants;
import com.github.saphyra.skyxplore.filter.CharacterIdCleanupFilter;
import com.github.saphyra.skyxplore.filter.HeaderSetupFilter;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class FilterConfiguration {
    private static final int FILTER_ORDER = 1;

    @Bean
    public FilterRegistrationBean<CharacterIdCleanupFilter> characterIdCleanupFilterFilterRegistrationBean(
        CharacterIdCleanupFilter characterIdCleanupFilter
    ) {
        log.info("CharacterIdCleanupFilter order: {}", FILTER_ORDER);
        FilterRegistrationBean<CharacterIdCleanupFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(characterIdCleanupFilter);
        filterRegistrationBean.setOrder(FILTER_ORDER);
        filterRegistrationBean.addUrlPatterns(RequestConstants.WEB_PREFIX + "/*");
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<HeaderSetupFilter> headerSetupFilterFilterRegistrationBean(
        HeaderSetupFilter headerSetupFilter
    ) {
        log.info("HeaderSetupFilter order: {}", FILTER_ORDER);
        FilterRegistrationBean<HeaderSetupFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(headerSetupFilter);
        filterRegistrationBean.setOrder(FILTER_ORDER);
        filterRegistrationBean.addUrlPatterns(RequestConstants.WEB_PREFIX + "/*", RequestConstants.WEB_PREFIX);
        return filterRegistrationBean;
    }
}
