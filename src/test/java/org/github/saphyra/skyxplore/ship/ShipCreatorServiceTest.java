package org.github.saphyra.skyxplore.ship;

import com.github.saphyra.util.IdGenerator;
import org.github.saphyra.skyxplore.gamedata.entity.Ship;
import org.github.saphyra.skyxplore.gamedata.subservice.ShipService;
import org.github.saphyra.skyxplore.ship.domain.EquippedShip;
import org.github.saphyra.skyxplore.ship.repository.EquippedShipDao;
import org.github.saphyra.skyxplore.slot.EquippedSlotFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.github.saphyra.skyxplore.common.ShipConstants.BATTERY_ID;
import static org.github.saphyra.skyxplore.common.ShipConstants.GENERATOR_ID;
import static org.github.saphyra.skyxplore.common.ShipConstants.STARTER_SHIP_ID;
import static org.github.saphyra.skyxplore.common.ShipConstants.STORAGE_ID;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ShipCreatorServiceTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String SHIP_ID = "ship_id";
    private static final Integer COREHULL = 32;
    private static final Integer CONNECTOR = 6;
    private static final String DEFENSE_SLOT_ID = "defense_slot_id";
    private static final String WEAPON_SLOT_ID = "weapon_slot_id";

    @Mock
    private  IdGenerator idGenerator;

    @Mock
    private  EquippedShipDao equippedShipDao;

    @Mock
    private  ShipService shipService;

    @Mock
    private  EquippedSlotFacade equippedSlotFacade;

    @Mock
    private Ship ship;

    @InjectMocks
    private ShipCreatorService underTest;

    @Test
    public void createShip(){
        //GIVEN
        given(idGenerator.generateRandomId()).willReturn(SHIP_ID);
        given(shipService.get(STARTER_SHIP_ID)).willReturn(ship);
        given(ship.getCoreHull()).willReturn(COREHULL);
        given(ship.getConnector()).willReturn(CONNECTOR);
        given(equippedSlotFacade.createDefenseSlot(SHIP_ID)).willReturn(DEFENSE_SLOT_ID);
        given(equippedSlotFacade.createWeaponSlot(SHIP_ID)).willReturn(WEAPON_SLOT_ID);
        //WHEN
        underTest.createShip(CHARACTER_ID);
        //THEN
        ArgumentCaptor<EquippedShip> argumentCaptor = ArgumentCaptor.forClass(EquippedShip.class);
        verify(equippedShipDao).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getCharacterId()).isEqualTo(CHARACTER_ID);
        assertThat(argumentCaptor.getValue().getShipId()).isEqualTo(SHIP_ID);
        assertThat(argumentCaptor.getValue().getShipType()).isEqualTo(STARTER_SHIP_ID);
        assertThat(argumentCaptor.getValue().getCoreHull()).isEqualTo(COREHULL);
        assertThat(argumentCaptor.getValue().getConnectorSlot()).isEqualTo(CONNECTOR);
        assertThat(argumentCaptor.getValue().getConnectorEquipped()).containsExactly(GENERATOR_ID, GENERATOR_ID, BATTERY_ID, BATTERY_ID, STORAGE_ID, STORAGE_ID);
        assertThat(argumentCaptor.getValue().getDefenseSlotId()).isEqualTo(DEFENSE_SLOT_ID);
        assertThat(argumentCaptor.getValue().getWeaponSlotId()).isEqualTo(WEAPON_SLOT_ID);
    }
}