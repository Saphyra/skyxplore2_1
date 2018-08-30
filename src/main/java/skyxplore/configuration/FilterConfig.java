package skyxplore.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import skyxplore.filter.CharacterAuthFilter;
import skyxplore.service.AccessTokenFacade;
import skyxplore.filter.AuthFilter;

@Configuration
@SuppressWarnings({"unused", "WeakerAccess"})
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<AuthFilter> authFilterBean (AuthFilter authFilter){
        FilterRegistrationBean<AuthFilter> authFilterBean = new FilterRegistrationBean<>();
        authFilterBean.setFilter(authFilter);
        authFilterBean.setOrder(1);
        return authFilterBean;
    }

    @Bean
    public FilterRegistrationBean<CharacterAuthFilter> characterAuthFilterBean(CharacterAuthFilter characterAuthFilter){
        FilterRegistrationBean<CharacterAuthFilter> characterAuthFilterBean = new FilterRegistrationBean<>();
        characterAuthFilterBean.setFilter(characterAuthFilter);
        characterAuthFilterBean.setOrder(10);
        return characterAuthFilterBean;
    }
}
