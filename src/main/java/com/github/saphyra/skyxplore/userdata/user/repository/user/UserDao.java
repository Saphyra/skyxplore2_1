package com.github.saphyra.skyxplore.userdata.user.repository.user;

import com.github.saphyra.converter.Converter;
import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.userdata.user.domain.SkyXpUser;
import lombok.extern.slf4j.Slf4j;
import com.github.saphyra.skyxplore.common.event.AccountDeletedEvent;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
@Slf4j
public class UserDao extends AbstractDao<UserEntity, SkyXpUser, String, UserRepository> {

    public UserDao(
        Converter<UserEntity, SkyXpUser> converter,
        UserRepository repository
    ) {
        super(converter, repository);
    }

    @Transactional
    @EventListener
    void delete(AccountDeletedEvent accountDeletedEvent) {
        log.info("Deleting user {}", accountDeletedEvent.getUserId());
        repository.deleteById(accountDeletedEvent.getUserId());
    }

    public Optional<SkyXpUser> findUserByEmail(String email) {
        return converter.convertEntity(repository.findByEmail(email));
    }
}
