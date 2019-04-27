package org.github.saphyra.skyxplore;

import com.github.saphyra.authservice.EnableAuthService;
import com.github.saphyra.encryption.EnableEncryption;
import com.github.saphyra.exceptionhandling.EnableExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@Slf4j
@EnableEncryption
@EnableExceptionHandler
@EnableAuthService
public class Application {
    public static ConfigurableApplicationContext APP_CTX = null;

    public static void main(String[] args) {
        APP_CTX = SpringApplication.run(Application.class, args);
    }
}
