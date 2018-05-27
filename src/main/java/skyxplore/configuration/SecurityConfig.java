package skyxplore.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String[] allowedURIs = new String[]{
            "/",
            "/registration",
            "/css/**",
            "/images/**",
            "/js/**",
            "/api/isusernameexists"
    };
    @Override
    public void configure(HttpSecurity security) throws Exception{
        security
            .authorizeRequests()
                .antMatchers(allowedURIs).permitAll()
                .anyRequest().authenticated()
            .and()
            .formLogin()
                .loginPage("/").permitAll()
            .and()
            .logout().logoutUrl("/logout").permitAll();
    }
}
