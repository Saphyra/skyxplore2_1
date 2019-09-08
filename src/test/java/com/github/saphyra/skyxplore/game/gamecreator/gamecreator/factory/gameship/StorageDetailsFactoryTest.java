package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.data.gamedata.entity.Storage;
import com.github.saphyra.skyxplore.data.gamedata.subservice.StorageService;
import com.github.saphyra.skyxplore.game.game.domain.ship.ShipEquipments;
import com.github.saphyra.skyxplore.game.game.domain.ship.StorageDetails;

@RunWith(MockitoJUnitRunner.class)
public class StorageDetailsFactoryTest {
    private static final String STORAGE_ID = "storage_id";
    private static final Integer CAPACITY = 25;

    @Mock
    private StorageService storageService;

    @InjectMocks
    private StorageDetailsFactory underTest;

    @Mock
    private ShipEquipments shipEquipments;

    @Mock
    private Storage storage;

    @Test
    public void create() {
        //GIVEN
        given(shipEquipments.getConnectorEquipped()).willReturn(Arrays.asList(STORAGE_ID));
        given(storageService.get(STORAGE_ID)).willReturn(storage);
        given(storage.getCapacity()).willReturn(CAPACITY);
        //WHEN
        StorageDetails result = underTest.create(shipEquipments);
        //THEN
        assertThat(result.getCapacity()).isEqualTo(CAPACITY);
        assertThat(result.getItems()).isEmpty();
    }
}