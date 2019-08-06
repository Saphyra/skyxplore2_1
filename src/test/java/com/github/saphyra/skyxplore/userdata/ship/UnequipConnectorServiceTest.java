package com.github.saphyra.skyxplore.userdata.ship;

import com.github.saphyra.skyxplore.userdata.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.userdata.ship.domain.EquippedShip;
import com.github.saphyra.skyxplore.userdata.ship.repository.EquippedShipDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class UnequipConnectorServiceTest {
    private static final String EQUIPMENT_ID = "equipment_id";

    @Mock
    private EquippedShipDao equippedShipDao;

    @Mock
    private EquipUtil equipUtil;

    @Mock
    private UnequipExtenderService unequipExtenderService;

    @Mock
    private SkyXpCharacter character;

    @Mock
    private EquippedShip ship;

    @InjectMocks
    private UnequipConnectorService underTest;

    @Test
    public void unequipConnector_extender() {
        //GIVEN
        given(equipUtil.isExtender(EQUIPMENT_ID)).willReturn(true);
        //WHEN
        underTest.unequipConnector(EQUIPMENT_ID, character, ship);
        //THEN
        verify(ship).removeConnector(EQUIPMENT_ID);
        verify(unequipExtenderService).unequipExtender(EQUIPMENT_ID, character, ship);
        verify(equippedShipDao).save(ship);
    }

    @Test
    public void unequipConnector() {
        //GIVEN
        given(equipUtil.isExtender(EQUIPMENT_ID)).willReturn(false);
        //WHEN
        underTest.unequipConnector(EQUIPMENT_ID, character, ship);
        //THEN
        verify(ship).removeConnector(EQUIPMENT_ID);
        verifyZeroInteractions(unequipExtenderService);
        verify(equippedShipDao).save(ship);
    }
}