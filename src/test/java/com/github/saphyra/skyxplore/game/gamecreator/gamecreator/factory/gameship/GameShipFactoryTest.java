package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.game.game.domain.Game;
import com.github.saphyra.skyxplore.game.game.domain.ship.Coordinates;
import com.github.saphyra.skyxplore.game.game.domain.ship.GameShip;
import com.github.saphyra.skyxplore.game.game.domain.ship.GameShipDetails;
import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroupCharacter;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ShipEquipmentsProvider;
import com.github.saphyra.util.IdGenerator;

@RunWith(MockitoJUnitRunner.class)
public class GameShipFactoryTest {
    private static final UUID GAME_SHIP_ID = UUID.randomUUID();

    @Mock
    private GameShipDetailsFactory gameShipDetailsFactory;

    @Mock
    private IdGenerator idGenerator;

    @Mock
    private ShipEquipmentsProvider shipEquipmentsProvider;

    @InjectMocks
    private GameShipFactory underTest;

    @Mock
    private GameGroupCharacter gameGroupCharacter;

    @Mock
    private Game game;

    @Mock
    private ShipEquipments shipEquipments;

    @Mock
    private GameShipDetails gameShipDetails;

    @Test
    public void create() {
        //GIVEN
        given(shipEquipmentsProvider.getEquipments(gameGroupCharacter, game)).willReturn(shipEquipments);
        given(idGenerator.randomUUID()).willReturn(GAME_SHIP_ID);
        given(gameShipDetailsFactory.create(shipEquipments)).willReturn(gameShipDetails);
        //WHEN
        GameShip result = underTest.create(gameGroupCharacter, game);
        //THEN
        assertThat(result.getGameShipId()).isEqualTo(GAME_SHIP_ID);
        assertThat(result.getPosition()).isEqualTo(Coordinates.createDefault());
        assertThat(result.getShipEquipments()).isEqualTo(shipEquipments);
        assertThat(result.getGameShipDetails()).isEqualTo(gameShipDetails);
    }
}