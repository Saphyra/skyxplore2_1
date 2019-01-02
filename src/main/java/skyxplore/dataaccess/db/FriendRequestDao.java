package skyxplore.dataaccess.db;

import com.github.saphyra.converter.Converter;
import com.github.saphyra.dao.AbstractDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.repository.FriendRequestRepository;
import skyxplore.domain.community.friendrequest.FriendRequest;
import skyxplore.domain.community.friendrequest.FriendRequestEntity;

import java.util.List;

@Component
@Slf4j
public class FriendRequestDao extends AbstractDao<FriendRequestEntity, FriendRequest, String, FriendRequestRepository> {

    public FriendRequestDao(Converter<FriendRequestEntity, FriendRequest> converter, FriendRequestRepository repository) {
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
