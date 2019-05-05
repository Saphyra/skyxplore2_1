package org.github.saphyra.skyxplore.user.repository.credentials;

import org.github.saphyra.skyxplore.testing.configuration.DataSourceConfiguration;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class CredentialsRepositoryTest {
    private static final String USER_ID_1 = "user_id_1";
    private static final String USER_ID_2 = "user_id_2";
    private static final String USER_NAME_1 = "user_name_1";
    private static final String USER_NAME_2 = "user_name_2";
    @Autowired
    private CredentialsRepository underTest;

    @After
    public void tearDown(){
        underTest.deleteAll();
    }

    @Test
    public void findByUserName(){
        //GIVEN
        CredentialsEntity credentialsEntity1 = new CredentialsEntity(USER_ID_1, USER_NAME_1, "");
        CredentialsEntity credentialsEntity2 = new CredentialsEntity(USER_ID_2, USER_NAME_2, "");
        underTest.saveAll(Arrays.asList(credentialsEntity1, credentialsEntity2));
        //WHEN
        Optional<CredentialsEntity> result = underTest.findByUserName(USER_NAME_1);
        //THEN
        assertThat(result).contains(credentialsEntity1);
    }

    @TestConfiguration
    @EnableJpaRepositories(basePackageClasses = CredentialsRepository.class)
    @EntityScan(basePackageClasses = CredentialsEntity.class)
    @Import(DataSourceConfiguration.class)
    static class TestConfig{

    }
}