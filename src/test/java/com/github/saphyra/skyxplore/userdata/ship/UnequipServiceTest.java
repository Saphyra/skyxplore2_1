package com.github.saphyra.skyxplore.userdata.ship;

import static com.github.saphyra.skyxplore.data.DataConstants.CONNECTOR_SLOT_NAME;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.userdata.character.CharacterQueryService;
import com.github.saphyra.skyxplore.userdata.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.userdata.character.repository.CharacterDao;
import com.github.saphyra.skyxplore.userdata.ship.domain.EquippedShip;
import com.github.saphyra.skyxplore.userdata.ship.domain.UnequipRequest;

@RunWith(MockitoJUnitRunner.class)
public class UnequipServiceTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String EQUIPMENT_ID = "equipment_id";
    private static final String EQUIPMENT_SLOT_NAME = "equipment_slot_name";

    @Mock
    private CharacterDao characterDao;

    @Mock
    private CharacterQueryService characterQueryService;

    @Mock
    private ShipQueryService shipQueryService;

    @Mock
    private UnequipConnectorService unequipConnectorService;

    @Mock
    private UnequipFromSlotService unequipFromSlotService;

    @Mock
    private SkyXpCharacter character;

    @InjectMocks
    private UnequipService underTest;

    @Mock
    private EquippedShip ship;

    @Test
    public void unequip_connector() {
        //GIVEN
        given(characterQueryService.findByCharacterIdValidated(CHARACTER_ID)).willReturn(character);

        given(shipQueryService.findShipByCharacterIdValidated(CHARACTER_ID)).willReturn(ship);

        UnequipRequest request = new UnequipRequest(CONNECTOR_SLOT_NAME, EQUIPMENT_ID);
        //WHEN
        underTest.unequip(request, CHARACTER_ID);
        //THEN
        verify(unequipConnectorService).unequipConnector(EQUIPMENT_ID, character, ship);
        verify(character).addEquipment(EQUIPMENT_ID);
        verify(characterDao).save(character);
    }

    @Test
    public void unequip_Equipment() {
        //GIVEN
        given(characterQueryService.findByCharacterIdValidated(CHARACTER_ID)).willReturn(character);

        given(shipQueryService.findShipByCharacterIdValidated(CHARACTER_ID)).willReturn(ship);

        UnequipRequest request = new UnequipRequest(EQUIPMENT_SLOT_NAME, EQUIPMENT_ID);
        //WHEN
        underTest.unequip(request, CHARACTER_ID);
        //THEN
        verify(unequipFromSlotService).unequipFromSlot(request, ship);
        verify(character).addEquipment(EQUIPMENT_ID);
        verify(characterDao).save(character);
    }
}