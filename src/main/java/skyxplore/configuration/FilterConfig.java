package skyxplore.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import skyxplore.service.AccessTokenFacade;
import skyxplore.filter.AuthFilter;

@Configuration
@SuppressWarnings({"unused", "WeakerAccess"})
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<AuthFilter> authFilterBean (AuthFilter authFilterBean){
        FilterRegistrationBean<AuthFilter> authFilter = new FilterRegistrationBean<>();
        authFilter.setFilter(authFilterBean);
        authFilter.setOrder(1);
        return authFilter;
    }
}
