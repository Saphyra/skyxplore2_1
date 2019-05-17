package com.github.saphyra.skyxplore.ship.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;

import com.github.saphyra.skyxplore.event.CharacterDeletedEvent;
import com.github.saphyra.skyxplore.event.ShipDeletedEvent;
import com.github.saphyra.skyxplore.ship.domain.EquippedShip;

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

    @Mock
    private EquippedShip equippedShip;

    @Test
    public void testDeleteByCharacterIdShouldDelete() {
        //GIVEN
        EquippedShipEntity equippedShip = EquippedShipEntity.builder()
            .shipId(EQUIPPED_SHIP_ID)
            .build();
        when(equippedShipRepository.findByCharacterId(CHARACTER_ID)).thenReturn(Optional.of(equippedShip));
        //WHEN
        underTest.deleteByCharacterId(new CharacterDeletedEvent(CHARACTER_ID));
        //THEN
        verify(equippedShipRepository).findByCharacterId(CHARACTER_ID);
        ArgumentCaptor<ShipDeletedEvent> argumentCaptor = ArgumentCaptor.forClass(ShipDeletedEvent.class);
        verify(eventPublisher).publishEvent(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getShipId()).isEqualTo(EQUIPPED_SHIP_ID);
        verify(equippedShipRepository).delete(equippedShip);
    }

    @Test
    public void testFindShipByCharacterIdShouldCallRepositoryAndConvert() {
        //GIVEN
        Optional<EquippedShipEntity> equippedShipEntity = Optional.of(EquippedShipEntity.builder().build());
        when(equippedShipRepository.findByCharacterId(CHARACTER_ID)).thenReturn(equippedShipEntity);

        when(equippedShipConverter.convertEntity(equippedShipEntity)).thenReturn(Optional.of(equippedShip));
        //WHEN
        Optional<EquippedShip> result = underTest.findShipByCharacterId(CHARACTER_ID);
        //THEN
        assertThat(result).contains(equippedShip);
    }
}