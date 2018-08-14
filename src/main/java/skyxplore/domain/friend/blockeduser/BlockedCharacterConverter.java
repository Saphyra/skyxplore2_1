package skyxplore.domain.friend.blockeduser;

import org.springframework.stereotype.Component;
import skyxplore.domain.ConverterBase;

@Component
public class BlockedCharacterConverter extends ConverterBase<BlockedUserEntity, BlockedUser> {
    @Override
    public BlockedUser convertEntity(BlockedUserEntity entity) {
        return BlockedUser.builder()
            .blockedUserEntityId(entity.getBlockedUserEntityId())
            .characterId(entity.getCharacterId())
            .blockedUserId(entity.getBlockedUserId())
            .build();
    }

    @Override
    public BlockedUserEntity convertDomain(BlockedUser domain) {
        return BlockedUserEntity.builder()
            .blockedUserEntityId(domain.getBlockedUserEntityId())
            .characterId(domain.getCharacterId())
            .blockedUserId(domain.getBlockedUserId())
            .build();
    }
}
