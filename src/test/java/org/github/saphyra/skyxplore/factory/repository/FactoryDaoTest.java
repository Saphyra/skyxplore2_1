package org.github.saphyra.skyxplore.factory.repository;

import org.github.saphyra.skyxplore.factory.domain.Factory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.github.saphyra.skyxplore.product.repository.ProductDao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FactoryDaoTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String FACTORY_ID = "factory_id";

    @Mock
    private FactoryRepository factoryRepository;

    @Mock
    private FactoryConverter factoryConverter;

    @Mock
    private ProductDao productDao;

    @InjectMocks
    private FactoryDao underTest;

    @Test
    public void testDeleteByCharacterIdShouldCallDeleteMethods() {
        //GIVEN
        FactoryEntity factoryEntity = FactoryEntity.builder()
            .factoryId(FACTORY_ID)
            .build();
        when(factoryRepository.findByCharacterId(CHARACTER_ID)).thenReturn(factoryEntity);
        //WHEN
        underTest.deleteByCharacterId(CHARACTER_ID);
        //THEN
        verify(productDao).deleteByFactoryId(FACTORY_ID);
        verify(factoryRepository).deleteByCharacterId(CHARACTER_ID);
    }

    @Test
    public void testFindByCharacterIdShouldCallRepositoryAndReturnDomain() {
        //GIVEN
        FactoryEntity entity = FactoryEntity.builder().build();
        when(factoryRepository.findByCharacterId(CHARACTER_ID)).thenReturn(entity);

        Factory factory = Factory.builder().build();
        when(factoryConverter.convertEntity(entity)).thenReturn(factory);
        //WHEN
        Factory result = underTest.findByCharacterId(CHARACTER_ID);
        //THEN
        verify(factoryRepository).findByCharacterId(CHARACTER_ID);
        verify(factoryConverter).convertEntity(entity);
        assertThat(result).isEqualTo(factory);
    }
}
