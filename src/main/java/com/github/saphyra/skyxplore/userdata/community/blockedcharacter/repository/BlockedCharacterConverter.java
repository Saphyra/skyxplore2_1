package com.github.saphyra.skyxplore.userdata.community.blockedcharacter.repository;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.skyxplore.userdata.community.blockedcharacter.domain.BlockedCharacter;
import org.springframework.stereotype.Component;

@Component
class BlockedCharacterConverter extends ConverterBase<BlockedCharacterEntity, BlockedCharacter> {
    @Override
    public BlockedCharacter processEntityConversion(BlockedCharacterEntity entity) {
        return BlockedCharacter.builder()
            .blockedCharacterEntityId(entity.getBlockedCharacterEntityId())
            .characterId(entity.getCharacterId())
            .blockedCharacterId(entity.getBlockedCharacterId())
            .build();
    }

    @Override
    public BlockedCharacterEntity processDomainConversion(BlockedCharacter domain) {
        return BlockedCharacterEntity.builder()
            .blockedCharacterEntityId(domain.getBlockedCharacterEntityId())
            .characterId(domain.getCharacterId())
            .blockedCharacterId(domain.getBlockedCharacterId())
            .build();
    }
}
