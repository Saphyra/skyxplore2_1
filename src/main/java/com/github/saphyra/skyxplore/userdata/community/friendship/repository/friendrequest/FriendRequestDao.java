package com.github.saphyra.skyxplore.userdata.community.friendship.repository.friendrequest;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.userdata.community.friendship.domain.FriendRequest;
import com.github.saphyra.skyxplore.common.event.CharacterDeletedEvent;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class FriendRequestDao extends AbstractDao<FriendRequestEntity, FriendRequest, String, FriendRequestRepository> {

    public FriendRequestDao(FriendRequestConverter converter, FriendRequestRepository repository) {
        super(converter, repository);
    }

    @EventListener
    void characterDeletedEventProcessor(CharacterDeletedEvent event) {
        repository.deleteByCharacterId(event.getCharacterId());
    }

    public List<FriendRequest> getByCharacterId(String characterId) {
        return converter.convertEntity(repository.getByCharacterId(characterId));
    }

    public List<FriendRequest> getByCharacterIdOrFriendId(String characterId, String friendId) {
        return converter.convertEntity(repository.getByCharacterIdOrFriendId(characterId, friendId));
    }

    public List<FriendRequest> getByFriendId(String characterId) {
        return converter.convertEntity(repository.getByFriendId(characterId));
    }
}
