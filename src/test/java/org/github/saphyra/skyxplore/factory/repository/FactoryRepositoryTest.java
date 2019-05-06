package org.github.saphyra.skyxplore.factory.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Optional;

import org.github.saphyra.skyxplore.testing.configuration.DataSourceConfiguration;
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
public class FactoryRepositoryTest {
    private static final String FACTORY_ID_1 = "factory_id_1";
    private static final String FACTORY_ID_2 = "factory_id_2";
    private static final String CHARACTER_ID_1 = "character_id_1";
    private static final String CHARACTER_ID_2 = "character_id_2";

    @Autowired
    private FactoryRepository underTest;

    @After
    public void tearDown(){
        underTest.deleteAll();
    }

    @Test
    public void findByCharacterId(){
        //GIVEN
        FactoryEntity entity1 = new FactoryEntity(FACTORY_ID_1, CHARACTER_ID_1, "");
        FactoryEntity entity2 = new FactoryEntity(FACTORY_ID_2, CHARACTER_ID_2, "");
        underTest.saveAll(Arrays.asList(entity1, entity2));
        //WHEN
        Optional<FactoryEntity> result = underTest.findByCharacterId(CHARACTER_ID_1);
        //THEN
        assertThat(result).contains(entity1);
    }

    @TestConfiguration
    @Import(DataSourceConfiguration.class)
    @EnableJpaRepositories(basePackageClasses = FactoryRepository.class)
    @EntityScan(basePackageClasses = FactoryEntity.class)
    @Profile("int-test")
    static class TestConfig{

    }
}