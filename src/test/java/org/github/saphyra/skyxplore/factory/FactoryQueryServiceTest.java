package org.github.saphyra.skyxplore.factory;

import org.github.saphyra.skyxplore.common.exception.FactoryNotFoundException;
import org.github.saphyra.skyxplore.factory.domain.Factory;
import org.github.saphyra.skyxplore.factory.domain.Materials;
import org.github.saphyra.skyxplore.factory.repository.FactoryDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FactoryQueryServiceTest {
    private static final String CHARACTER_ID = "character_id";
    private static final Integer AMOUNT = 3;
    private static final String MATERIAL_ID = "material_id";
    private static final String FACTORY_ID = "factory_id";
    @Mock
    private FactoryDao factoryDao;

    @InjectMocks
    private FactoryQueryService underTest;

    @Test(expected = FactoryNotFoundException.class)
    public void testFindFactoryOfCharacterValidatedShouldThrowExceptionWhenNotFound() {
        //GIVEN
        when(factoryDao.findByCharacterId(CHARACTER_ID)).thenReturn(Optional.empty());
        //WHEN
        underTest.findFactoryOfCharacterValidated(CHARACTER_ID);
    }

    @Test
    public void testFindFactoryOfCharacterValidatedShouldReturn() {
        //GIVEN
        Factory factory = Factory.builder().build();
        when(factoryDao.findByCharacterId(CHARACTER_ID)).thenReturn(Optional.of(factory));
        //WHEN
        Factory result = underTest.findFactoryOfCharacterValidated(CHARACTER_ID);
        //THEN
        verify(factoryDao).findByCharacterId(CHARACTER_ID);
        assertThat(result).isEqualTo(factory);
    }

    @Test
    public void testGetMaterialsShouldReturn() {
        //GIVEN
        Materials materials = new Materials();
        materials.addMaterial(MATERIAL_ID, AMOUNT);

        Factory factory = Factory.builder()
            .materials(materials)
            .build();
        when(factoryDao.findByCharacterId(CHARACTER_ID)).thenReturn(Optional.of(factory));

        //WHEN
        Map<String, Integer> result = underTest.getMaterials(CHARACTER_ID);
        //THEN
        verify(factoryDao).findByCharacterId(CHARACTER_ID);
        assertThat(result).containsValue(AMOUNT);
        assertThat(result).containsKey(MATERIAL_ID);
    }

    @Test
    public void testGetFactoryIdOfCharacterShouldReturn() {
        //GIVEN
        Factory factory = Factory.builder()
            .factoryId(FACTORY_ID)
            .build();
        when(factoryDao.findByCharacterId(CHARACTER_ID)).thenReturn(Optional.of(factory));
        //WHEN
        String result = underTest.getFactoryIdOfCharacter(CHARACTER_ID);
        //THEN
        assertThat(result).isEqualTo(FACTORY_ID);
    }

    @Test(expected = FactoryNotFoundException.class)
    public void testFindByFactoryIdShouldThrowExceptionWhenNotFound() {
        //GIVEN
        when(factoryDao.findById(FACTORY_ID)).thenReturn(Optional.empty());
        //WHEN
        underTest.findByFactoryId(FACTORY_ID);
    }

    @Test
    public void testFindByFactoryIdShouldReturn() {
        //GIVEN
        Factory factory = Factory.builder().build();
        when(factoryDao.findById(FACTORY_ID)).thenReturn(Optional.of(factory));
        //WHEN
        Factory result = underTest.findByFactoryId(FACTORY_ID);
        //THEN
        verify(factoryDao).findById(FACTORY_ID);
        assertThat(result).isEqualTo(factory);
    }
}