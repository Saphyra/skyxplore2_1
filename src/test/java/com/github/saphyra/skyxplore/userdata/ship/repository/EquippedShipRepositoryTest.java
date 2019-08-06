package com.github.saphyra.skyxplore.userdata.ship.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Optional;

import com.github.saphyra.testing.configuration.DataSourceConfiguration;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("int-test")
public class EquippedShipRepositoryTest {
    private static final String CHARACTER_ID_1 = "character_id_1";
    private static final String CHARACTER_ID_2 = "character_id_2";
    private static final String SHIP_ID_1 = "ship_id_1";
    private static final String SHIP_ID_2 = "ship_id_2";

    @Autowired
    private EquippedShipRepository underTest;

    @After
    public void tearDown(){
        underTest.deleteAll();
    }

    @Test
    public void findByCharacterId(){
        //GIVEN
        EquippedShipEntity entity1 = EquippedShipEntity.builder()
            .shipId(SHIP_ID_1)
            .characterId(CHARACTER_ID_1)
            .connectorEquipped("")
            .connectorSlot("")
            .coreHull("")
            .defenseSlotId("")
            .weaponSlotId("")
            .shipType("")
            .build();

        EquippedShipEntity entity2 = EquippedShipEntity.builder()
            .shipId(SHIP_ID_2)
            .characterId(CHARACTER_ID_2)
            .connectorEquipped("")
            .connectorSlot("")
            .coreHull("")
            .defenseSlotId("")
            .weaponSlotId("")
            .shipType("")
            .build();

        underTest.saveAll(Arrays.asList(entity1, entity2));
        //WHEN
        Optional<EquippedShipEntity> result = underTest.findByCharacterId(CHARACTER_ID_1);
        //THEN
        assertThat(result).contains(entity1);
    }

    @TestConfiguration
    @EnableJpaRepositories(basePackageClasses = EquippedShipRepository.class)
    @EntityScan(basePackageClasses = EquippedShipEntity.class)
    @Import(DataSourceConfiguration.class)
    @Profile("int-test")
    static class TestConfig{

    }
}