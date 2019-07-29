package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.ainame;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class AiNameConfiguration {
    @Bean
    public FirstNames firstNames(
        ApplicationContext applicationContext,
        ObjectMapper objectMapperWrapper
    ) throws IOException {
        Resource resource = applicationContext.getResource("classpath:public/gamedata/ainame/first_name.json");
        return objectMapperWrapper.readValue(resource.getInputStream(), FirstNames.class);
    }

    @Bean
    public LastNames lastNames(
        ApplicationContext applicationContext,
        ObjectMapper objectMapperWrapper
    ) throws IOException {
        Resource resource = applicationContext.getResource("classpath:public/gamedata/ainame/last_name.json");
        return objectMapperWrapper.readValue(resource.getInputStream(), LastNames.class);
    }
}
