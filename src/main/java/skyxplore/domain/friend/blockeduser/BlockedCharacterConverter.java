package skyxplore.domain.friend.blockeduser;

import org.springframework.stereotype.Component;

import skyxplore.domain.ConverterBase;

@Component
public class BlockedCharacterConverter extends ConverterBase<BlockedCharacteEntity, BlockedCharacter> {
    @Override
    public BlockedCharacter convertEntity(BlockedCharacteEntity entity) {
        if (entity == null) {
            return null;
        }
        return BlockedCharacter.builder()
            .blockedCharacterEntityId(entity.getBlockedCharacterEntityId())
            .characterId(entity.getCharacterId())
            .blockedCharacterId(entity.getBlockedCharacterId())
            .build();
    }

    @Override
    public BlockedCharacteEntity convertDomain(BlockedCharacter domain) {
        if (domain == null) {
            return null;
        }
        return BlockedCharacteEntity.builder()
            .blockedCharacterEntityId(domain.getBlockedCharacterEntityId())
            .characterId(domain.getCharacterId())
            .blockedCharacterId(domain.getBlockedCharacterId())
            .build();
    }
}
