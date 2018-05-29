package skyxplore.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import skyxplore.filter.DefaultExceptionHandlerFilter;
import skyxplore.filter.AuthFilter;
import skyxplore.auth.service.AccessTokenService;
import skyxplore.filter.AuthExceptionHandlerFilter;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<DefaultExceptionHandlerFilter> exceptionHandlerFilterBean(){
        FilterRegistrationBean<DefaultExceptionHandlerFilter> filter = new FilterRegistrationBean<>();
        filter.setFilter(defaultExceptionFilter());
        filter.setOrder(Ordered.LOWEST_PRECEDENCE);
        return filter;
    }

    @Bean
    public DefaultExceptionHandlerFilter defaultExceptionFilter(){
        return new DefaultExceptionHandlerFilter();
    }

    @Bean
    public FilterRegistrationBean<AuthExceptionHandlerFilter> authExceptionHandlerFilterFilterBean(){
        FilterRegistrationBean<AuthExceptionHandlerFilter> filter = new FilterRegistrationBean<>();
        filter.setFilter(authExceptionHandlerFilter());
        filter.setOrder(1);
        return filter;
    }

    @Bean
    public AuthExceptionHandlerFilter authExceptionHandlerFilter(){
        return new AuthExceptionHandlerFilter();
    }

    @Bean
    public FilterRegistrationBean<AuthFilter> authFilterBean (AccessTokenService authService){
        FilterRegistrationBean<AuthFilter> authFilter = new FilterRegistrationBean<>();
        authFilter.setFilter(loginFilter(authService));
        authFilter.setOrder(2);
        return authFilter;
    }

    @Bean
    public AuthFilter loginFilter(AccessTokenService authService){
        return new AuthFilter(authService);
    }
}
