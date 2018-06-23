package skyxplore.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import skyxplore.service.AccessTokenService;
import skyxplore.filter.AuthFilter;

@Configuration
@SuppressWarnings({"unused", "WeakerAccess"})
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<AuthFilter> authFilterBean (AccessTokenService authService){
        FilterRegistrationBean<AuthFilter> authFilter = new FilterRegistrationBean<>();
        authFilter.setFilter(loginFilter(authService));
        authFilter.setOrder(1);
        return authFilter;
    }

    @Bean
    public AuthFilter loginFilter(AccessTokenService authService){
        return new AuthFilter(authService);
    }
}
