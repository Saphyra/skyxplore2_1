package org.github.saphyra.skyxplore.ship.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.github.saphyra.skyxplore.ship.domain.EquippedShip;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.github.saphyra.skyxplore.slot.repository.SlotDao;
import skyxplore.exception.ShipNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class EquippedShipDaoTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String EQUIPPED_SHIP_ID = "equipped_ship_id";
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
        when(equippedShipRepository.getByCharacterId(CHARACTER_ID)).thenReturn(null);
        //WHEN
        underTest.deleteByCharacterId(CHARACTER_ID);
    }

    @Test
    public void testDeleteByCharacterIdShouldDelete() {
        //GIVEN
        EquippedShipEntity equippedShip = EquippedShipEntity.builder()
            .shipId(EQUIPPED_SHIP_ID)
            .build();
        when(equippedShipRepository.getByCharacterId(CHARACTER_ID)).thenReturn(equippedShip);
        //WHEN
        underTest.deleteByCharacterId(CHARACTER_ID);
        //THEN
        verify(equippedShipRepository).getByCharacterId(CHARACTER_ID);
        verify(slotDao).deleteByShipId(EQUIPPED_SHIP_ID);
        verify(equippedShipRepository).delete(equippedShip);
    }

    @Test
    public void testGetShipByCharacterIdShouldCallRepositoryAndConvert() {
        //GIVEN
        EquippedShipEntity equippedShipEntity = EquippedShipEntity.builder().build();
        when(equippedShipRepository.getByCharacterId(CHARACTER_ID)).thenReturn(equippedShipEntity);

        EquippedShip equippedShip = EquippedShip.builder().build();
        when(equippedShipConverter.convertEntity(equippedShipEntity)).thenReturn(equippedShip);
        //WHEN
        EquippedShip result = underTest.getShipByCharacterId(CHARACTER_ID);
        //THEN
        verify(equippedShipRepository).getByCharacterId(CHARACTER_ID);
        verify(equippedShipConverter).convertEntity(equippedShipEntity);
        assertThat(result).isEqualTo(equippedShip);
    }
}