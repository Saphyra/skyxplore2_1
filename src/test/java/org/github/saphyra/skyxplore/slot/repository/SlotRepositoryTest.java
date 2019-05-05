package org.github.saphyra.skyxplore.slot.repository;

import org.github.saphyra.skyxplore.testing.configuration.DataSourceConfiguration;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SlotRepositoryTest.TestConfig.class)
public class SlotRepositoryTest {
    private static final String SHIP_ID_1 = "ship_id_1";
    private static final String SHIP_ID_2 = "ship_id_2";
    private static final String SLOT_ID_1 = "slot_id_1";
    private static final String SLOT_ID_2 = "slot_id_2";
    private static final String SLOT_ID_3 = "slot_id_3";

    @Autowired
    private SlotRepository underTest;

    @After
    public void tearDown() {
        underTest.deleteAll();
    }

    @Test
    public void deleteByShipId() {
        //GIVEN
        SlotEntity entity1 = createSlotEntity(SLOT_ID_1, SHIP_ID_1);
        SlotEntity entity2 = createSlotEntity(SLOT_ID_2, SHIP_ID_1);
        SlotEntity entity3 = createSlotEntity(SLOT_ID_3, SHIP_ID_2);

        underTest.saveAll(Arrays.asList(entity1, entity2, entity3));
        //WHEN
        underTest.deleteByShipId(SHIP_ID_1);
        //THEN
        assertThat(underTest.findAll()).containsOnly(entity3);
    }

    private SlotEntity createSlotEntity(String slotId, String shipId) {
        return SlotEntity.builder()
            .slotId(slotId)
            .shipId(shipId)
            .frontSlot("")
            .frontEquipped("")
            .leftSlot("")
            .leftEquipped("")
            .rightSlot("")
            .rightEquipped("")
            .backSlot("")
            .backEquipped("")
            .build();
    }

    @TestConfiguration
    @EntityScan(basePackageClasses = SlotEntity.class)
    @EnableJpaRepositories(basePackageClasses = SlotRepository.class)
    @Import(DataSourceConfiguration.class)
    static class TestConfig {

    }
}