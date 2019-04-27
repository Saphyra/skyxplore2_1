package skyxplore.dataaccess.db;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.dataaccess.db.repository.EquippedShipRepository;
import skyxplore.domain.ship.EquippedShip;
import skyxplore.domain.ship.EquippedShipConverter;
import skyxplore.domain.ship.EquippedShipEntity;
import skyxplore.exception.ShipNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class EquippedShipDaoTest {
    @Mock
    private EquippedShipRepository equippedShipRepository;

    @Mock
    private EquippedShipConverter equippedShipConverter;

    @Mock
    private SlotDao slotDao;

    @InjectMocks
    private EquippedShipDao underTest;

    @Test(expected = ShipNotFoundException.class)
    public void testDeleteByCharacterIdShouldThrowExceptionWhenShipNotFound() {
        //GIVEN
        when(equippedShipRepository.getByCharacterId(CHARACTER_ID_1)).thenReturn(null);
        //WHEN
        underTest.deleteByCharacterId(CHARACTER_ID_1);
    }

    @Test
    public void testDeleteByCharacterIdShouldDelete() {
        //GIVEN
        EquippedShipEntity equippedShip = createEquippedShipEntity();
        when(equippedShipRepository.getByCharacterId(CHARACTER_ID_1)).thenReturn(equippedShip);
        //WHEN
        underTest.deleteByCharacterId(CHARACTER_ID_1);
        //THEN
        verify(equippedShipRepository).getByCharacterId(CHARACTER_ID_1);
        verify(slotDao).deleteByShipId(EQUIPPED_SHIP_ID);
        verify(equippedShipRepository).delete(equippedShip);
    }

    @Test
    public void testGetShipByCharacterIdShouldCallRepositoryAndConvert() {
        //GIVEN
        EquippedShipEntity equippedShipEntity = createEquippedShipEntity();
        when(equippedShipRepository.getByCharacterId(CHARACTER_ID_1)).thenReturn(equippedShipEntity);

        EquippedShip equippedShip = createEquippedShip();
        when(equippedShipConverter.convertEntity(equippedShipEntity)).thenReturn(equippedShip);
        //WHEN
        EquippedShip result = underTest.getShipByCharacterId(CHARACTER_ID_1);
        //THEN
        verify(equippedShipRepository).getByCharacterId(CHARACTER_ID_1);
        verify(equippedShipConverter).convertEntity(equippedShipEntity);
        assertEquals(equippedShip, result);
    }
}