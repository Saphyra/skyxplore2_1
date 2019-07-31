package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.item;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
//TODO unit test
public class ItemProvider {
    private final ShipProvider shipProvider;

    public String getRandomShip() {
        return shipProvider.getRandomShip();
    }
}
