package com.github.saphyra.skyxplore.userdata.community.friendship.repository.friendship;

import com.github.saphyra.converter.Converter;
import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.event.CharacterDeletedEvent;
import lombok.extern.slf4j.Slf4j;
import com.github.saphyra.skyxplore.userdata.community.friendship.domain.Friendship;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class FriendshipDao extends AbstractDao<FriendshipEntity, Friendship, String, FriendshipRepository> {

    public FriendshipDao(Converter<FriendshipEntity, Friendship> converter, FriendshipRepository repository) {
        super(converter, repository);
    }

    @EventListener
    public void deleteByCharacterId(CharacterDeletedEvent event) {
        repository.deleteByCharacterId(event.getCharacterId());
    }

    public List<Friendship> getByCharacterIdOrFriendId(String characterId, String friendId) {
        return converter.convertEntity(repository.getByCharacterIdOrFriendId(characterId, friendId));
    }

    public List<Friendship> getFriendshipsOfCharacter(String characterId) {
        return converter.convertEntity(repository.getFriendshipsOfCharacter(characterId));
    }
}
