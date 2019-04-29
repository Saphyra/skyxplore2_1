package org.github.saphyra.skyxplore.character;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@Getter
//TODO make package-private
public class CharacterGeneratorConfig {
    @Value("${character.new.materials}")
    private int startMaterials;

    @Value("${character.new.money}")
    private int startMoney;
}
