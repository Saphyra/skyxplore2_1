package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.saphyra.skyxplore.game.game.domain.TeamColor;

@Configuration
public class FactoryBeanConfiguration {
    @Bean
    public List<TeamColor> teamColors(){
        return Arrays.asList(TeamColor.values());
    }
}
