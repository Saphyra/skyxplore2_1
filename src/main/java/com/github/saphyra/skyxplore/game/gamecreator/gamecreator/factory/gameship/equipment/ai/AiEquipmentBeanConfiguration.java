package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai;

import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.domain.UpgradableSlot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class AiEquipmentBeanConfiguration {
    @Bean
    public List<UpgradableSlot> upgradableSlots() {
        return Arrays.stream(UpgradableSlot.values())
            .filter(upgradableSlot -> !upgradableSlot.name().startsWith("TEST_"))
            .collect(Collectors.toList());
    }
}
