package skyxplore.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class CharacterGeneratorConfig {
    @Value("${character.new.materials}")
    private int startMaterials;

    @Value("${character.new.money}")
    private int startMoney;
}
