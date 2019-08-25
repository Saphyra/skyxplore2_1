package com.github.saphyra.skyxplore.userdata.factory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.userdata.factory.domain.Factory;
import com.github.saphyra.skyxplore.userdata.factory.domain.Materials;
import com.github.saphyra.skyxplore.userdata.factory.repository.FactoryDao;

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

    @Mock
    private Factory factory;

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

        given(factory.getMaterials()).willReturn(materials);
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
        given(factory.getFactoryId()).willReturn(FACTORY_ID);
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
        when(factoryDao.findById(FACTORY_ID)).thenReturn(Optional.of(factory));
        //WHEN
        Factory result = underTest.findByFactoryId(FACTORY_ID);
        //THEN
        verify(factoryDao).findById(FACTORY_ID);
        assertThat(result).isEqualTo(factory);
    }
}