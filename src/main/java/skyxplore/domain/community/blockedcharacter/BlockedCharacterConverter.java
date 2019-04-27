package skyxplore.domain.community.blockedcharacter;

import org.springframework.stereotype.Component;

import com.github.saphyra.converter.ConverterBase;

@Component
public class BlockedCharacterConverter extends ConverterBase<BlockedCharacterEntity, BlockedCharacter> {
    @Override
    public BlockedCharacter processEntityConversion(BlockedCharacterEntity entity) {
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
    public BlockedCharacterEntity processDomainConversion(BlockedCharacter domain) {
        if (domain == null) {
            throw new IllegalArgumentException("domain must not be null.");
        }
        return BlockedCharacterEntity.builder()
            .blockedCharacterEntityId(domain.getBlockedCharacterEntityId())
            .characterId(domain.getCharacterId())
            .blockedCharacterId(domain.getBlockedCharacterId())
            .build();
    }
}
