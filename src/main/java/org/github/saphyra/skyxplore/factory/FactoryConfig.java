package org.github.saphyra.skyxplore.factory;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
class FactoryConfig {
    @Value("${character.new.materials}")
    private int startMaterials;
}
