package skyxplore.domain.community.blockeduser;

import org.springframework.stereotype.Component;

import skyxplore.domain.ConverterBase;

@Component
//TODO unit test
public class BlockedCharacterConverter extends ConverterBase<BlockedCharacterEntity, BlockedCharacter> {
    @Override
    public BlockedCharacter convertEntity(BlockedCharacterEntity entity) {
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
    public BlockedCharacterEntity convertDomain(BlockedCharacter domain) {
        if(domain == null){
            throw new IllegalArgumentException("domain must not be null.");
        }
        return BlockedCharacterEntity.builder()
            .blockedCharacterEntityId(domain.getBlockedCharacterEntityId())
            .characterId(domain.getCharacterId())
            .blockedCharacterId(domain.getBlockedCharacterId())
            .build();
    }
}
