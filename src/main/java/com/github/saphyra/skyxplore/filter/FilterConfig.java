package com.github.saphyra.skyxplore.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class FilterConfig {
    @Bean
    FilterRegistrationBean<CharacterAuthFilter> characterAuthFilterBean(CharacterAuthFilter characterAuthFilter) {
        FilterRegistrationBean<CharacterAuthFilter> characterAuthFilterBean = new FilterRegistrationBean<>();
        characterAuthFilterBean.setFilter(characterAuthFilter);
        characterAuthFilterBean.setOrder(10);
        return characterAuthFilterBean;
    }

    @Bean
    FilterRegistrationBean<AlreadyInLobbyFilter> alreadyInLobbyFilterBean(AlreadyInLobbyFilter alreadyInLobbyFilter) {
        FilterRegistrationBean<AlreadyInLobbyFilter> alreadyInLobbyFilterBean = new FilterRegistrationBean<>();
        alreadyInLobbyFilterBean.setFilter(alreadyInLobbyFilter);
        alreadyInLobbyFilterBean.setOrder(20);
        return alreadyInLobbyFilterBean;
    }

    @Bean
    FilterRegistrationBean<LobbyAccessFilter> lobbyAccessFilterBean(LobbyAccessFilter lobbyAccessFilter) {
        FilterRegistrationBean<LobbyAccessFilter> lobbyAccessFilterBean = new FilterRegistrationBean<>();
        lobbyAccessFilterBean.setFilter(lobbyAccessFilter);
        lobbyAccessFilterBean.setOrder(25);
        return lobbyAccessFilterBean;
    }

    @Bean
    FilterRegistrationBean<CookieCleanupFilter> cookieCleanupFilterBean(CookieCleanupFilter cookieCleanupFilter) {
        FilterRegistrationBean<CookieCleanupFilter> cookieCleanupFilterBean = new FilterRegistrationBean<>();
        cookieCleanupFilterBean.setFilter(cookieCleanupFilter);
        cookieCleanupFilterBean.setOrder(100);
        return cookieCleanupFilterBean;
    }
}