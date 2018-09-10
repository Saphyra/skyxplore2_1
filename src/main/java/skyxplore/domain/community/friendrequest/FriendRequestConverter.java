package skyxplore.domain.community.friendrequest;

import org.springframework.stereotype.Component;
import skyxplore.domain.ConverterBase;

@Component
public class FriendRequestConverter extends ConverterBase<FriendRequestEntity, FriendRequest> {
    @Override
    public FriendRequest convertEntity(FriendRequestEntity entity) {
        if(entity == null){
            return null;
        }
        return FriendRequest.builder()
            .friendRequestId(entity.getFriendRequestId())
            .characterId(entity.getCharacterId())
            .friendId(entity.getFriendId())
            .build();
    }

    @Override
    public FriendRequestEntity convertDomain(FriendRequest domain) {
        if(domain == null){
            throw new IllegalArgumentException("domain must not be null.");
        }
        return FriendRequestEntity.builder()
            .friendRequestId(domain.getFriendRequestId())
            .characterId(domain.getCharacterId())
            .friendId(domain.getFriendId())
            .build();
    }
}
