package com.github.saphyra.skyxplore.ship.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import com.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.gamedata.entity.Extender;
import com.github.saphyra.skyxplore.gamedata.subservice.ExtenderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;

@RunWith(MockitoJUnitRunner.class)
public class EquippedShipTest {
    private static final String EQUIPPED_SHIP_ID = "equipped_ship_id";
    private static final String CHARACTER_ID = "character_id";
    private static final String SHIP_TYPE = "ship_type";
    private static final Integer COREHULL = 10000;
    private static final Integer CONNECTOR_SLOT = 6;
    private static final String DEFENSE_SLOT_ID = "defense_slot_id";
    private static final String WEAPON_SLOT_ID = "weapon_slot_id";
    private static final String CONNECTOR_1 = "connector_1";
    private static final String CONNECTOR_2 = "connector_2";

    @Mock
    private ExtenderService extenderService;

    @Mock
    private SkyXpCharacter character;

    @InjectMocks
    private EquippedShip underTest;

    @Before
    public void init() {
        underTest = EquippedShip.builder()
            .shipId(EQUIPPED_SHIP_ID)
            .characterId(CHARACTER_ID)
            .shipType(SHIP_TYPE)
            .coreHull(COREHULL)
            .connectorSlot(CONNECTOR_SLOT)
            .defenseSlotId(DEFENSE_SLOT_ID)
            .weaponSlotId(WEAPON_SLOT_ID)
            .build();
    }

    @Test(expected = BadRequestException.class)
    public void testAddConnectorShouldThrowExceptionWhenLimitReached() {
        //GIVEN
        underTest.setConnectorSlot(0);
        //WHEN
        underTest.addConnector(CONNECTOR_1);
    }

    @Test
    public void testAddConnectorShouldAdd() {
        //WHEN
        underTest.addConnector(CONNECTOR_1);
        //THEN
        assertThat(underTest.getConnectorEquipped()).containsOnly(CONNECTOR_1);
    }

    @Test
    public void testAddConnectorsShouldAdd() {
        //GIVEN
        List<String> connectors = Arrays.asList(CONNECTOR_1, CONNECTOR_2);
        //WHEN
        underTest.addConnectors(connectors);
        //THEN
        assertThat(underTest.getConnectorEquipped())
            .contains(CONNECTOR_1)
            .contains(CONNECTOR_2);
    }

    @Test(expected = BadRequestException.class)
    public void testRemoveConnectorShouldThrowExceptionWhenNotEquipped() {
        //WHEN
        underTest.removeConnector(CONNECTOR_1);
    }

    @Test
    public void testRemoveConnectorShouldRemove() {
        //GIVEN
        underTest.addConnector(CONNECTOR_1);
        //WHEN
        underTest.removeConnector(CONNECTOR_1);
        //THEN
        assertThat(underTest.getConnectorEquipped()).isEmpty();
    }

    @Test
    public void testAddConnectorSlotShouldAdd() {
        //WHEN
        underTest.addConnectorSlot(2);
        //THEN
        assertThat(underTest.getConnectorSlot()).isEqualTo(CONNECTOR_SLOT + 2);
    }

    @Test
    public void testRemoveConnectorSlotShouldNotRemoveExtender() {
        //GIVEN
        underTest.setConnectorSlot(4);
        underTest.addConnectors(Arrays.asList(CONNECTOR_1, CONNECTOR_1, CONNECTOR_2, CONNECTOR_2));
        when(extenderService.get(CONNECTOR_1)).thenReturn(new Extender());
        when(extenderService.get(CONNECTOR_2)).thenReturn(null);
        //WHEN
        underTest.removeConnectorSlot(2, character, extenderService);
        //THEN
        verify(extenderService, times(6)).get(anyString());
        verify(character, times(2)).addEquipment(CONNECTOR_2);
        assertThat(underTest.getConnectorEquipped()).containsOnly(CONNECTOR_1);
        assertThat(underTest.getConnectorSlot()).isEqualTo(2);
    }

    @Test
    public void testGetEquipmentsShouldReturnsClone() {
        //GIVEN
        underTest.addConnector(CONNECTOR_1);
        //WHEN
        List<String> result = underTest.getConnectorEquipped();
        result.add(CONNECTOR_1);
        //THEN
        assertThat(underTest.getConnectorEquipped()).hasSize(1);
    }
}
