package skyxplore.dataaccess.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.repository.FriendshipRepository;
import skyxplore.domain.community.friendship.Friendship;
import skyxplore.domain.community.friendship.FriendshipConverter;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class FriendshipDao {
    private final FriendshipConverter friendshipConverter;
    private final FriendshipRepository friendshipRepository;

    public void delete(Friendship friendship) {
        friendshipRepository.delete(friendshipConverter.convertDomain(friendship));
    }

    public List<Friendship> getByCharacterIdOrFriendId(String characterId, String friendId) {
        return friendshipConverter.convertEntity(friendshipRepository.getByCharacterIdOrFriendId(characterId, friendId));
    }

    public List<Friendship> getFriendshipsOfCharacter(String characterId) {
        return friendshipConverter.convertEntity(friendshipRepository.getFriendshipsOfCharacter(characterId));
    }
}
