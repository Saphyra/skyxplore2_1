package org.github.saphyra.skyxplore.community.friendship.repository.friendrequest;

import java.util.List;

import org.github.saphyra.skyxplore.community.friendship.domain.FriendRequest;
import org.springframework.stereotype.Component;

import com.github.saphyra.dao.AbstractDao;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FriendRequestDao extends AbstractDao<FriendRequestEntity, FriendRequest, String, FriendRequestRepository> {

    public FriendRequestDao(FriendRequestConverter converter, FriendRequestRepository repository) {
        super(converter, repository);
    }

    public void deleteByCharacterId(String characterId) {
        repository.deleteByCharacterId(characterId);
    }

    public List<FriendRequest> getByCharacterId(String characterId) {
        return converter.convertEntity(repository.findByCharacterId(characterId));
    }

    public List<FriendRequest> getByCharacterIdOrFriendId(String characterId, String friendId) {
        return converter.convertEntity(repository.findByCharacterIdOrFriendId(characterId, friendId));
    }

    public List<FriendRequest> getByFriendId(String characterId) {
        return converter.convertEntity(repository.getByFriendId(characterId));
    }
}
