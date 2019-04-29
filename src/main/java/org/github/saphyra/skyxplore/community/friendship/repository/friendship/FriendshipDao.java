package org.github.saphyra.skyxplore.community.friendship.repository.friendship;

import java.util.List;

import org.github.saphyra.skyxplore.community.friendship.domain.Friendship;
import org.springframework.stereotype.Component;

import com.github.saphyra.converter.Converter;
import com.github.saphyra.dao.AbstractDao;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FriendshipDao extends AbstractDao<FriendshipEntity, Friendship, String, FriendshipRepository> {

    public FriendshipDao(Converter<FriendshipEntity, Friendship> converter, FriendshipRepository repository) {
        super(converter, repository);
    }

    public void deleteByCharacterId(String characterId) {
        repository.deleteByCharacterId(characterId);
    }

    public List<Friendship> getByCharacterIdOrFriendId(String characterId, String friendId) {
        return converter.convertEntity(repository.getByCharacterIdOrFriendId(characterId, friendId));
    }

    Friendship getByFriendshipId(String friendshipId) {
        return repository.findById(friendshipId).map(converter::convertEntity).orElse(null);
    }

    public List<Friendship> getFriendshipsOfCharacter(String characterId) {
        return converter.convertEntity(repository.getFriendshipsOfCharacter(characterId));
    }
}
