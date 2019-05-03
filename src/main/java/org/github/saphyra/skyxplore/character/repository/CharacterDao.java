package org.github.saphyra.skyxplore.character.repository;

import java.util.List;
import java.util.Optional;

import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.event.AccountDeletedEvent;
import org.github.saphyra.skyxplore.event.CharacterDeletedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.github.saphyra.dao.AbstractDao;
import lombok.extern.slf4j.Slf4j;

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
        List<SkyXpCharacter> characters = findByUserId(accountDeletedEvent.getUserId());
        characters.forEach(e -> deleteById(e.getCharacterId()));
    }

    public Optional<SkyXpCharacter> findByCharacterName(String characterName) {
        return converter.convertEntity(repository.findByCharacterName(characterName));
    }

    public List<SkyXpCharacter> findCharacterByNameLike(String name) {
        return converter.convertEntity(repository.findByCharacterNameContaining(name));
    }

    public List<SkyXpCharacter> findByUserId(String userId) {
        return converter.convertEntity(repository.findByUserId(userId));
    }
}
