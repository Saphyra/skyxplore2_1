package org.github.saphyra.skyxplore;

import com.github.saphyra.authservice.EnableAuthService;
import com.github.saphyra.encryption.EnableEncryption;
import com.github.saphyra.exceptionhandling.EnableExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@Slf4j
@EnableEncryption
@EnableExceptionHandler
@EnableAuthService

//TODO update scans
@ComponentScan(basePackages = {
    "skyxplore",
    "org.github.saphyra.skyxplore"
})
@EnableJpaRepositories(basePackages = {
    "skyxplore",
    "org.github.saphyra.skyxplore"
})
@EntityScan(basePackages = {
    "skyxplore",
    "org.github.saphyra.skyxplore"
})
public class Application {
    public static ConfigurableApplicationContext APP_CTX = null;

    public static void main(String[] args) {
        APP_CTX = SpringApplication.run(Application.class, args);
    }
}
