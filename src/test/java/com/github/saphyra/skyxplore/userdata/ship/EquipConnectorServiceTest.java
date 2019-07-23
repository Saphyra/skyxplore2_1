package com.github.saphyra.skyxplore.userdata.ship;

import com.github.saphyra.skyxplore.userdata.ship.domain.EquipRequest;
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
public class EquipConnectorServiceTest {
    private static final String EQUIPMENT_ID = "equipment_id";

    @Mock
    private EquippedShipDao equippedShipDao;

    @Mock
    private EquipUtil equipUtil;

    @Mock
    private EquipExtenderService equipExtenderService;

    @Mock
    private EquippedShip ship;

    @InjectMocks
    private EquipConnectorService underTest;

    @Test
    public void equipExtender(){
        //GIVEN
        given(equipUtil.isExtender(EQUIPMENT_ID)).willReturn(true);

        EquipRequest request = new EquipRequest(EQUIPMENT_ID, "");
        //WHEN
        underTest.equipConnector(request, ship);
        //THEN
        verify(equipExtenderService).equipExtender(EQUIPMENT_ID, ship);
        verify(ship).addConnector(EQUIPMENT_ID);
        verify(equippedShipDao).save(ship);
    }

    @Test
    public void equipConnector(){
        //GIVEN
        given(equipUtil.isExtender(EQUIPMENT_ID)).willReturn(false);

        EquipRequest request = new EquipRequest(EQUIPMENT_ID, "");
        //WHEN
        underTest.equipConnector(request, ship);
        //THEN
        verifyZeroInteractions(equipExtenderService);
        verify(ship).addConnector(EQUIPMENT_ID);
        verify(equippedShipDao).save(ship);
    }
}