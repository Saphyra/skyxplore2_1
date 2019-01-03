package skyxplore;

import com.github.saphyra.encryption.EnableEncryption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@Slf4j
@EnableEncryption
public class Application {
    public static ConfigurableApplicationContext APP_CTX = null;

    public static void main(String[] args) {
        APP_CTX = SpringApplication.run(Application.class, args);
    }
}
