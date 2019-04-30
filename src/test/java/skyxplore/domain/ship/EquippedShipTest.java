package skyxplore.domain.ship;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.github.saphyra.skyxplore.gamedata.entity.Extender;
import org.github.saphyra.skyxplore.gamedata.subservice.ExtenderService;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_1;
import static skyxplore.testutil.TestUtils.DATA_CONNECTOR;
import static skyxplore.testutil.TestUtils.DATA_ELEMENT;
import static skyxplore.testutil.TestUtils.DATA_SHIP_CONNECTOR_SLOT;
import static skyxplore.testutil.TestUtils.DATA_SHIP_COREHULL;
import static skyxplore.testutil.TestUtils.DEFENSE_SLOT_ID;
import static skyxplore.testutil.TestUtils.EQUIPPED_SHIP_ID;
import static skyxplore.testutil.TestUtils.EQUIPPED_SHIP_TYPE;
import static skyxplore.testutil.TestUtils.WEAPON_SLOT_ID;

@RunWith(MockitoJUnitRunner.class)
public class EquippedShipTest {
    @Mock
    private ExtenderService extenderService;

    @Mock
    private SkyXpCharacter character;

    @InjectMocks
    private EquippedShip underTest;

    @Before
    public void init() {
        underTest = new EquippedShip();
        underTest.setShipId(EQUIPPED_SHIP_ID);
        underTest.setCharacterId(CHARACTER_ID_1);
        underTest.setShipType(EQUIPPED_SHIP_TYPE);
        underTest.setCoreHull(DATA_SHIP_COREHULL);
        underTest.setConnectorSlot(DATA_SHIP_CONNECTOR_SLOT);
        underTest.setDefenseSlotId(DEFENSE_SLOT_ID);
        underTest.setWeaponSlotId(WEAPON_SLOT_ID);
    }

    @Test(expected = BadRequestException.class)
    public void testAddConnectorShouldThrowExceptionWhenLimitReached() {
        //GIVEN
        underTest.setConnectorSlot(0);
        //WHEN
        underTest.addConnector(DATA_CONNECTOR);
    }

    @Test
    public void testAddConnectorShouldAdd() {
        //WHEN
        underTest.addConnector(DATA_CONNECTOR);
        //THEN
        assertTrue(underTest.getConnectorEquipped().contains(DATA_CONNECTOR));
    }

    @Test
    public void testAddConnectorsShouldAdd() {
        //GIVEN
        List<String> connectors = Arrays.asList(DATA_CONNECTOR, DATA_ELEMENT);
        //WHEN
        underTest.addConnectors(connectors);
        //THEN
        assertTrue(underTest.getConnectorEquipped().contains(DATA_CONNECTOR));
        assertTrue(underTest.getConnectorEquipped().contains(DATA_ELEMENT));
    }

    @Test(expected = BadRequestException.class)
    public void testRemoveConnectorShouldThrowExceptionWhenNotEquipped() {
        //WHEN
        underTest.removeConnector(DATA_CONNECTOR);
    }

    @Test
    public void testRemoveConnectorShouldRemove() {
        //GIVEN
        underTest.addConnector(DATA_CONNECTOR);
        //WHEN
        underTest.removeConnector(DATA_CONNECTOR);
        //THEN
        assertFalse(underTest.getConnectorEquipped().contains(DATA_CONNECTOR));
    }

    @Test
    public void testAddConnectorSlotShouldAdd() {
        //WHEN
        underTest.addConnectorSlot(2);
        //THEN
        assertEquals((long) 2 + DATA_SHIP_CONNECTOR_SLOT, (long) underTest.getConnectorSlot());
    }

    @Test
    public void testRemoveConnectorSlotShouldNotRemoveExtender() {
        //GIVEN
        underTest.setConnectorSlot(4);
        underTest.addConnectors(Arrays.asList(DATA_CONNECTOR, DATA_CONNECTOR, DATA_ELEMENT, DATA_ELEMENT));
        when(extenderService.get(DATA_CONNECTOR)).thenReturn(new Extender());
        when(extenderService.get(DATA_ELEMENT)).thenReturn(null);
        //WHEN
        underTest.removeConnectorSlot(2, character, extenderService);
        //THEN
        verify(extenderService, times(6)).get(anyString());
        verify(character, times(2)).addEquipment(DATA_ELEMENT);
        assertTrue(underTest.getConnectorEquipped().contains(DATA_CONNECTOR));
        assertFalse(underTest.getConnectorEquipped().contains(DATA_ELEMENT));
        assertEquals((long) 2, (long) underTest.getConnectorSlot());
    }

    @Test
    public void testGetEquipmentsShouldReturnsClone() {
        //GIVEN
        underTest.addConnector(DATA_CONNECTOR);
        //WHEN
        List<String> result = underTest.getConnectorEquipped();
        result.add(DATA_CONNECTOR);
        //THEN
        assertEquals(1, underTest.getConnectorEquipped().size());
    }
}
