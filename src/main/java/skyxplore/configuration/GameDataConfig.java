package skyxplore.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import skyxplore.dataaccess.gamedata.AbilityService;

@Configuration
public class GameDataConfig {
    @Bean
    public AbilityService abilityService(){
        AbilityService abilityService = new AbilityService("src/main/resources/data/gamedata/ability");
        abilityService.init();
        return abilityService;
    }
}
