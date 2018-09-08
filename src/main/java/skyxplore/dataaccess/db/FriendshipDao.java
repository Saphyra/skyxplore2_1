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

    public void deleteByCharacterId(String characterId) {
        friendshipRepository.deleteByCharacterId(characterId);
    }

    public List<Friendship> getByCharacterIdOrFriendId(String characterId, String friendId) {
        return friendshipConverter.convertEntity(friendshipRepository.getByCharacterIdOrFriendId(characterId, friendId));
    }

    public Friendship getByFriendshipId(String friendshipId) {
        return friendshipRepository.findById(friendshipId).map(friendshipConverter::convertEntity).orElse(null);
    }

    public List<Friendship> getFriendshipsOfCharacter(String characterId) {
        return friendshipConverter.convertEntity(friendshipRepository.getFriendshipsOfCharacter(characterId));
    }

    public void save(Friendship friendship) {
        friendshipRepository.save(friendshipConverter.convertDomain(friendship));
    }
}
