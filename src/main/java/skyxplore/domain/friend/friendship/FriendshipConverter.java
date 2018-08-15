package skyxplore.domain.friend.friendship;

import org.springframework.stereotype.Component;

import skyxplore.domain.ConverterBase;

@Component
public class FriendshipConverter extends ConverterBase<FriendshipEntity, Friendship> {
    @Override
    public Friendship convertEntity(FriendshipEntity entity) {
        if(entity == null){
            return null;
        }
        Friendship friendship = new Friendship();
        friendship.setFriendshipId(entity.getFriendshipId());
        friendship.setCharacterId(entity.getFriendId());
        friendship.setFriendId(entity.getFriendId());
        return friendship;
    }

    @Override
    public FriendshipEntity convertDomain(Friendship domain) {
        if(domain == null){
            return null;
        }
        FriendshipEntity entity = new FriendshipEntity();
        entity.setFriendshipId(domain.getFriendshipId());
        entity.setCharacterId(domain.getCharacterId());
        entity.setFriendId(domain.getFriendId());
        return entity;
    }
}
