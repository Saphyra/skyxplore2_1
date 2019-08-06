package com.github.saphyra.skyxplore.userdata.character.repository;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.userdata.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.common.event.AccountDeletedEvent;
import com.github.saphyra.skyxplore.common.event.CharacterDeletedEvent;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class CharacterDao extends AbstractDao<CharacterEntity, SkyXpCharacter, String, CharacterRepository> {
    private final ApplicationEventPublisher applicationEventPublisher;

    public CharacterDao(
        CharacterConverter converter,
        CharacterRepository repository,
        ApplicationEventPublisher applicationEventPublisher) {
        super(converter, repository);
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void deleteById(String characterId) {
        applicationEventPublisher.publishEvent(new CharacterDeletedEvent(characterId));
        log.info("Deleting character {}", characterId);
        super.deleteById(characterId);
    }

    @EventListener
    void deleteByUserId(AccountDeletedEvent accountDeletedEvent) {
        List<SkyXpCharacter> characters = getByUserId(accountDeletedEvent.getUserId());
        characters.forEach(e -> deleteById(e.getCharacterId()));
    }

    public Optional<SkyXpCharacter> findByCharacterName(String characterName) {
        return converter.convertEntity(repository.findByCharacterName(characterName));
    }

    public List<SkyXpCharacter> getCharacterByNameLike(String name) {
        return converter.convertEntity(repository.getByCharacterNameContaining(name));
    }

    public List<SkyXpCharacter> getByUserId(String userId) {
        return converter.convertEntity(repository.getByUserId(userId));
    }
}
