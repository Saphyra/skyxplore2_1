package com.github.saphyra.skyxplore.character;

import com.github.saphyra.skyxplore.common.exception.CharacterNotFoundException;
import com.github.saphyra.skyxplore.common.exception.InvalidAccessException;
import com.github.saphyra.skyxplore.community.blockedcharacter.domain.BlockedCharacter;
import com.github.saphyra.skyxplore.community.blockedcharacter.repository.BlockedCharacterDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.github.saphyra.skyxplore.character.cache.CharacterNameLikeCache;
import com.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.character.repository.CharacterDao;
import com.github.saphyra.skyxplore.community.friendship.FriendshipQueryService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CharacterQueryService {
    private final BlockedCharacterDao blockedCharacterDao;
    private final CharacterNameLikeCache characterNameLikeCache;
    private final CharacterDao characterDao;
    private final FriendshipQueryService friendshipQueryService;

    public SkyXpCharacter findByCharacterId(String characterId) {
        return characterDao.findById(characterId)
            .orElseThrow(() -> new CharacterNotFoundException("Character not found with id " + characterId));
    }

    public SkyXpCharacter findCharacterByIdAuthorized(String characterId, String userId) {
        SkyXpCharacter character = findByCharacterId(characterId);
        if (!userId.equals(character.getUserId())) {
            throw new InvalidAccessException("Unauthorized character access. CharacterId: " + character.getCharacterId() + ", userId: " + userId);
        }
        return character;
    }

    public List<SkyXpCharacter> getCharactersCanBeAddressee(String characterId, String name) {
        SkyXpCharacter character = findByCharacterId(characterId);
        return getCharactersOfNameLike(name).stream()
            .filter(c -> isNotOwnCharacter(c, character.getUserId())) //Filtering own characters
            .filter(c -> isNotBlocked(character, c))  //Filtering characters blocked by the character
            .filter(c -> isNotBlocked(c, character))  //Filtering characters that blocks the character
            .collect(Collectors.toList());
    }

    public List<SkyXpCharacter> getCharactersCanBeBlocked(String name, String characterId) {
        SkyXpCharacter character = findByCharacterId(characterId);
        return getCharactersOfNameLike(name).stream()
            .filter(c -> isNotOwnCharacter(c, character.getUserId())) //Filtering own characters
            .filter(c -> isNotBlocked(character, c))  //Filtering characters blocked by the character
            .filter(c -> isNotBlocked(c, character))  //Filtering characters that blocks the character
            .collect(Collectors.toList());
    }

    public List<SkyXpCharacter> getCharactersCanBeFriend(String name, String characterId) {
        SkyXpCharacter character = findByCharacterId(characterId);
        return getCharactersOfNameLike(name).stream()
            .filter(c -> isNotOwnCharacter(c, character.getUserId())) //Filtering own characters
            .filter(c -> isNotBlocked(character, c))  //Filtering characters blocked by the character
            .filter(c -> isNotBlocked(c, character))  //Filtering characters that blocks the character
            .filter(c -> isFriend(c, character)) //Filtering friends
            .filter(c -> isRequestSent(c, character)) //Filtering characters friend request already sent
            .collect(Collectors.toList());
    }

    private boolean isFriend(SkyXpCharacter c, SkyXpCharacter character) {
        return !friendshipQueryService.isFriendshipAlreadyExists(c.getCharacterId(), character.getCharacterId());
    }

    private boolean isRequestSent(SkyXpCharacter c, SkyXpCharacter character) {
        return !friendshipQueryService.isFriendRequestAlreadyExists(c.getCharacterId(), character.getCharacterId());
    }

    private List<SkyXpCharacter> getCharactersOfNameLike(String name) {
        return characterNameLikeCache.get(name).orElse(Collections.emptyList());
    }

    private boolean isNotOwnCharacter(SkyXpCharacter character, String userId) {
        return !userId.equals(character.getUserId());
    }

    private boolean isNotBlocked(SkyXpCharacter character, SkyXpCharacter ownedCharacter) {
        List<BlockedCharacter> blockedCharacters = blockedCharacterDao.getBlockedCharacters(character.getCharacterId());
        return blockedCharacters.stream().noneMatch(blocked -> blocked.getBlockedCharacterId().equals(ownedCharacter.getCharacterId()));
    }

    public List<SkyXpCharacter> getCharactersByUserId(String userId) {
        return characterDao.getByUserId(userId);
    }

    List<String> getEquipmentsOfCharacter(String characterId) {
        return findByCharacterId(characterId).getEquipments();
    }

    Integer getMoneyOfCharacter(String characterId) {
        SkyXpCharacter character = findByCharacterId(characterId);
        return character.getMoney();
    }

    public boolean isCharNameExists(String charName) {
        return characterDao.findByCharacterName(charName).isPresent();
    }
}