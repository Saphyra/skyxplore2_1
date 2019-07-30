package com.github.saphyra.skyxplore.userdata.slot.repository;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.common.event.ShipDeletedEvent;

@RunWith(MockitoJUnitRunner.class)
public class SlotDaoTest {
    private static final String EQUIPPED_SHIP_ID = "equipped_ship_id";

    @Mock
    private SlotRepository slotRepository;

    @InjectMocks
    private SlotDao underTest;

    @Test
    public void testDeleteByShipIdShouldCallRepository() {
        //WHEN
        underTest.deleteByShipId(new ShipDeletedEvent(EQUIPPED_SHIP_ID));
        //THEN
        verify(slotRepository).deleteByShipId(EQUIPPED_SHIP_ID);
    }
}