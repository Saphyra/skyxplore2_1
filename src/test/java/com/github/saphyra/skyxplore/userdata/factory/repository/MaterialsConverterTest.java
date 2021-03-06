package com.github.saphyra.skyxplore.userdata.factory.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.saphyra.encryption.impl.StringEncryptor;
import com.github.saphyra.skyxplore.common.ObjectMapperDelegator;
import com.github.saphyra.skyxplore.userdata.factory.domain.Materials;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MaterialsConverterTest {
    private static final String ENCRYPTED_MATERIAL = "encrypted_material";
    private static final String FACTORY_ID = "factory_id";
    private static final String MATERIAL_ENTITY = "material_entity";
    private static final String MATERIAL_ID = "material_id";
    private static final Integer AMOUNT = 3;

    @Mock
    private ObjectMapperDelegator objectMapperDelegator;

    @Mock
    private StringEncryptor stringEncryptor;

    @InjectMocks
    private MaterialsConverter underTest;

    @Test
    public void testConvertEntityShouldReturnNullWhenNull() {
        //WHEN
        Materials result = underTest.convertEntity(null, null);
        //THEN
        assertThat(result).isNull();
    }

    @Test
    public void testConvertEntityShouldDecryptAndConvert() {
        //GIVEN
        when(stringEncryptor.decryptEntity(ENCRYPTED_MATERIAL, FACTORY_ID)).thenReturn(MATERIAL_ENTITY);
        HashMap<String, Integer> map = new HashMap<>();
        map.put(MATERIAL_ID, AMOUNT);
        when(objectMapperDelegator.readValue(eq(MATERIAL_ENTITY), Mockito.<TypeReference<HashMap<String, Integer>>>any())).thenReturn(map);
        //WHEN
        Materials result = underTest.convertEntity(ENCRYPTED_MATERIAL, FACTORY_ID);
        //THEN
        assertThat(result).isEqualTo(map);
    }

    @Test
    public void testConvertDomainShouldConvertAndEncrypt() {
        //GIVEN
        Materials materials = new Materials();

        when(objectMapperDelegator.writeValueAsString(materials)).thenReturn(MATERIAL_ENTITY);
        when(stringEncryptor.encryptEntity(MATERIAL_ENTITY, FACTORY_ID)).thenReturn(ENCRYPTED_MATERIAL);
        //WHEN
        String result = underTest.convertDomain(materials, FACTORY_ID);
        //THEN
        verify(objectMapperDelegator).writeValueAsString(materials);
        verify(stringEncryptor).encryptEntity(MATERIAL_ENTITY, FACTORY_ID);
        assertThat(result).isEqualTo(ENCRYPTED_MATERIAL);
    }
}