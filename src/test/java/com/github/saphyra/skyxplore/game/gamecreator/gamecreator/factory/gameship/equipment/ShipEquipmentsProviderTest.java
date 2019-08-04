package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment;

import com.github.saphyra.skyxplore.game.game.domain.Game;
import com.github.saphyra.skyxplore.game.game.domain.ship.GameShip;
import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroupCharacter;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai.AiShipEquipmentGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ShipEquipmentsProviderTest {
    private static final String CHARACTER_ID = "character-id";
    @Mock
    private AiShipEquipmentGenerator aiShipEquipmentGenerator;

    @Mock
    private ShipEquipmentAggregator shipEquipmentAggregator;

    @InjectMocks
    private ShipEquipmentsProvider underTest;

    @Mock
    private GameGroupCharacter gameGroupCharacter;

    @Mock
    private Game game;

    @Mock
    private GameShip gameShip;

    @Mock
    private ShipEquipments shipEquipments;

    @Test
    public void getEquipments_ai() {
        //GIVEN
        given(gameGroupCharacter.isAi()).willReturn(true);
        given(game.getShips()).willReturn(Arrays.asList(gameShip));
        given(aiShipEquipmentGenerator.generateEquipments(Arrays.asList(gameShip))).willReturn(shipEquipments);
        //WHEN
        ShipEquipments result = underTest.getEquipments(gameGroupCharacter, game);
        //THEN
        assertThat(result).isEqualTo(shipEquipments);
    }

    @Test
    public void getEquipments_normal() {
        //GIVEN
        given(gameGroupCharacter.isAi()).willReturn(false);
        given(gameGroupCharacter.getCharacterId()).willReturn(CHARACTER_ID);
        given(shipEquipmentAggregator.aggregateEquipments(CHARACTER_ID)).willReturn(shipEquipments);
        //WHEN
        ShipEquipments result = underTest.getEquipments(gameGroupCharacter, game);
        //THEN
        assertThat(result).isEqualTo(shipEquipments);
    }
}