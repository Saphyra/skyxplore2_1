package skyxplore.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import skyxplore.filter.CharacterAuthFilter;
import skyxplore.filter.CookieCleanupFilter;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<CharacterAuthFilter> characterAuthFilterBean(CharacterAuthFilter characterAuthFilter) {
        FilterRegistrationBean<CharacterAuthFilter> characterAuthFilterBean = new FilterRegistrationBean<>();
        characterAuthFilterBean.setFilter(characterAuthFilter);
        characterAuthFilterBean.setOrder(10);
        return characterAuthFilterBean;
    }

    @Bean
    public FilterRegistrationBean<CookieCleanupFilter> cookieCleanupFilterBean(CookieCleanupFilter cookieCleanupFilter) {
        FilterRegistrationBean<CookieCleanupFilter> cookieCleanupFilterBean = new FilterRegistrationBean<>();
        cookieCleanupFilterBean.setFilter(cookieCleanupFilter);
        cookieCleanupFilterBean.setOrder(100);
        return cookieCleanupFilterBean;
    }
}
