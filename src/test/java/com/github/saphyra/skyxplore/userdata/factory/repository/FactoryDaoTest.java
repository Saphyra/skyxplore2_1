package com.github.saphyra.skyxplore.userdata.factory.repository;

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

import com.github.saphyra.skyxplore.common.event.CharacterDeletedEvent;
import com.github.saphyra.skyxplore.common.event.FactoryDeletedEvent;
import com.github.saphyra.skyxplore.userdata.factory.domain.Factory;

@RunWith(MockitoJUnitRunner.class)
public class FactoryDaoTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String FACTORY_ID = "factory_id";

    @Mock
    private FactoryRepository factoryRepository;

    @Mock
    private FactoryConverter factoryConverter;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Mock
    private Factory factory;

    @InjectMocks
    private FactoryDao underTest;

    @Test
    public void testDeleteByCharacterIdShouldCallDeleteMethods() {
        //GIVEN
        FactoryEntity factoryEntity = FactoryEntity.builder()
            .factoryId(FACTORY_ID)
            .build();
        when(factoryRepository.findByCharacterId(CHARACTER_ID)).thenReturn(Optional.of(factoryEntity));
        //WHEN
        underTest.deleteByCharacterId(new CharacterDeletedEvent(CHARACTER_ID));
        //THEN
        ArgumentCaptor<FactoryDeletedEvent> argumentCaptor = ArgumentCaptor.forClass(FactoryDeletedEvent.class);
        verify(eventPublisher).publishEvent(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getFactoryId()).isEqualTo(FACTORY_ID);
        verify(factoryRepository).delete(factoryEntity);
    }

    @Test
    public void testFindByCharacterIdShouldCallRepositoryAndReturnDomain() {
        //GIVEN
        Optional<FactoryEntity> entity = Optional.of(FactoryEntity.builder().build());
        when(factoryRepository.findByCharacterId(CHARACTER_ID)).thenReturn(entity);

        when(factoryConverter.convertEntity(entity)).thenReturn(Optional.of(factory));
        //WHEN
        Optional<Factory> result = underTest.findByCharacterId(CHARACTER_ID);
        //THEN
        verify(factoryRepository).findByCharacterId(CHARACTER_ID);
        verify(factoryConverter).convertEntity(entity);
        assertThat(result).contains(factory);
    }
}
