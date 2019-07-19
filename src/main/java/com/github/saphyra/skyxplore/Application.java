package com.github.saphyra.skyxplore;

import com.github.saphyra.authservice.redirection.EnableRedirection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.github.saphyra.authservice.auth.EnableAuthService;
import com.github.saphyra.encryption.EnableEncryption;
import com.github.saphyra.exceptionhandling.EnableExceptionHandler;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
@EnableEncryption
@EnableExceptionHandler
@EnableAuthService
@EnableRedirection
@EnableJpaRepositories
@EntityScan
public class Application {
    public static ConfigurableApplicationContext APP_CTX = null;

    public static void main(String[] args) {
        APP_CTX = SpringApplication.run(Application.class, args);
    }
}
