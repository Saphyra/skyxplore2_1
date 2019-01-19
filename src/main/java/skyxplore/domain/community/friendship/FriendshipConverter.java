package skyxplore.domain.community.friendship;

import org.springframework.stereotype.Component;

import com.github.saphyra.converter.ConverterBase;

@Component
public class FriendshipConverter extends ConverterBase<FriendshipEntity, Friendship> {
    @Override
    public Friendship processEntityConversion(FriendshipEntity entity) {
        if(entity == null){
            return null;
        }
        Friendship friendship = new Friendship();
        friendship.setFriendshipId(entity.getFriendshipId());
        friendship.setCharacterId(entity.getCharacterId());
        friendship.setFriendId(entity.getFriendId());
        return friendship;
    }

    @Override
    public FriendshipEntity processDomainConversion(Friendship domain) {
        if(domain == null){
            throw new IllegalArgumentException("Friendship must not be null.");
        }
        FriendshipEntity entity = new FriendshipEntity();
        entity.setFriendshipId(domain.getFriendshipId());
        entity.setCharacterId(domain.getCharacterId());
        entity.setFriendId(domain.getFriendId());
        return entity;
    }
}
