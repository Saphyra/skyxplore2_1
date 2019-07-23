package com.github.saphyra.skyxplore.userdata.character;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@Getter
class CharacterConfig {
    @Value("${character.new.money}")
    private int startMoney;
}
