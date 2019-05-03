package org.github.saphyra.skyxplore.common.configuration;

import com.github.saphyra.util.IdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class BeanConfig {

    @Bean
    IdGenerator idGenerator() {
        return new IdGenerator();
    }
}
