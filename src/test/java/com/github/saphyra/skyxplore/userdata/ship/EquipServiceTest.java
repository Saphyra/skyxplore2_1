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
import com.github.saphyra.skyxplore.userdata.ship.domain.EquipRequest;
import com.github.saphyra.skyxplore.userdata.ship.domain.EquippedShip;

@RunWith(MockitoJUnitRunner.class)
public class EquipServiceTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String EQUIPMENT_ID = "equipment_id";

    @Mock
    private CharacterDao characterDao;

    @Mock
    private CharacterQueryService characterQueryService;

    @Mock
    private EquipConnectorService equipConnectorService;

    @Mock
    private EquipToSlotService equipToSlotService;

    @Mock
    private ShipQueryService shipQueryService;

    @Mock
    private SkyXpCharacter character;

    @InjectMocks
    private EquipService underTest;

    @Mock
    private EquippedShip ship;

    @Test
    public void equip_connector() {
        //GIVEN
        given(characterQueryService.findByCharacterId(CHARACTER_ID)).willReturn(character);

        given(shipQueryService.findShipbyCharacterIdValidated(CHARACTER_ID)).willReturn(ship);
        EquipRequest request = new EquipRequest(EQUIPMENT_ID, CONNECTOR_SLOT_NAME);
        //WHEN
        underTest.equip(request, CHARACTER_ID);
        //THEN
        verify(character).removeEquipment(EQUIPMENT_ID);
        verify(equipConnectorService).equipConnector(request, ship);
        verify(characterDao).save(character);
    }

    @Test
    public void equip_equipment() {
        //GIVEN
        given(characterQueryService.findByCharacterId(CHARACTER_ID)).willReturn(character);

        given(shipQueryService.findShipbyCharacterIdValidated(CHARACTER_ID)).willReturn(ship);
        EquipRequest request = new EquipRequest(EQUIPMENT_ID, "asd");
        //WHEN
        underTest.equip(request, CHARACTER_ID);
        //THEN
        verify(character).removeEquipment(EQUIPMENT_ID);
        verify(equipToSlotService).equipToSlot(request, ship);
        verify(characterDao).save(character);
    }
}