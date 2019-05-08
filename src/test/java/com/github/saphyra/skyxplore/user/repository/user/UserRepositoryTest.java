package com.github.saphyra.skyxplore.user.repository.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Optional;

import org.assertj.core.api.Assertions;

import com.github.saphyra.skyxplore.testing.configuration.DataSourceConfiguration;
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
public class UserRepositoryTest {
    private static final String USER_ID_1 = "user_id_1";
    private static final String USER_ID_2 = "user_id_2";
    private static final String EMAIL_1 = "email_1";
    private static final String EMAIL_2 = "email_2";
    @Autowired
    private UserRepository underTest;

    @After
    public void tearDown() {
        underTest.deleteAll();
    }

    @Test
    public void findByEmail() {
        //GIVEN
        UserEntity entity1 = new UserEntity(USER_ID_1, "", EMAIL_1);
        UserEntity entity2 = new UserEntity(USER_ID_2, "", EMAIL_2);
        underTest.saveAll(Arrays.asList(entity1, entity2));
        //WHEN
        Optional<UserEntity> result = underTest.findByEmail(EMAIL_1);
        //THEN
        Assertions.assertThat(result).contains(entity1);
    }


    @TestConfiguration
    @EnableJpaRepositories(basePackageClasses = UserRepository.class)
    @EntityScan(basePackageClasses = UserEntity.class)
    @Import(DataSourceConfiguration.class)
    @Profile("int-test")
    static class TestConfig {

    }
}