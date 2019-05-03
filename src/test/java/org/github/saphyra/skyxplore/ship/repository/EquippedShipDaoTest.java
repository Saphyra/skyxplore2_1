package org.github.saphyra.skyxplore.ship.repository;

import org.github.saphyra.skyxplore.event.CharacterDeletedEvent;
import org.github.saphyra.skyxplore.event.ShipDeletedEvent;
import org.github.saphyra.skyxplore.ship.domain.EquippedShip;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EquippedShipDaoTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String EQUIPPED_SHIP_ID = "equipped_ship_id";
    @Mock
    private EquippedShipRepository equippedShipRepository;

    @Mock
    private EquippedShipConverter equippedShipConverter;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private EquippedShipDao underTest;

    @Test
    public void testDeleteByCharacterIdShouldDelete() {
        //GIVEN
        EquippedShipEntity equippedShip = EquippedShipEntity.builder()
            .shipId(EQUIPPED_SHIP_ID)
            .build();
        when(equippedShipRepository.getByCharacterId(CHARACTER_ID)).thenReturn(equippedShip);
        //WHEN
        underTest.deleteByCharacterId(new CharacterDeletedEvent(CHARACTER_ID));
        //THEN
        verify(equippedShipRepository).getByCharacterId(CHARACTER_ID);
        ArgumentCaptor<ShipDeletedEvent> argumentCaptor = ArgumentCaptor.forClass(ShipDeletedEvent.class);
        verify(eventPublisher).publishEvent(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getShipId()).isEqualTo(EQUIPPED_SHIP_ID);
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