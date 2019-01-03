package skyxplore.service.character;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.cache.CharacterNameLikeCache;
import skyxplore.controller.view.View;
import skyxplore.controller.view.equipment.EquipmentViewList;
import skyxplore.dataaccess.db.CharacterDao;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.domain.community.blockedcharacter.BlockedCharacter;
import skyxplore.exception.CharacterNotFoundException;
import skyxplore.exception.InvalidAccessException;
import skyxplore.service.GameDataFacade;
import skyxplore.service.community.BlockedCharacterQueryService;
import skyxplore.service.community.FriendshipQueryService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("WeakerAccess")
@Slf4j
@RequiredArgsConstructor
@Service
public class CharacterQueryService {
    private final BlockedCharacterQueryService blockedCharacterQueryService;
    private final CharacterNameLikeCache characterNameLikeCache;
    private final CharacterDao characterDao;
    private final GameDataFacade gameDataFacade;
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

    public List<SkyXpCharacter> getBlockedCharacters(String characterId) {
        return blockedCharacterQueryService.getBlockedCharactersOf(characterId).stream()
            .map(blockedCharacter -> findByCharacterId(blockedCharacter.getBlockedCharacterId()))
            .collect(Collectors.toList());
    }

    public List<SkyXpCharacter> getCharactersCanBeAddressee(String name, String characterId) {
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
        List<BlockedCharacter> blockedCharacters = blockedCharacterQueryService.getBlockedCharactersOf(character.getCharacterId());
        return blockedCharacters.stream().noneMatch(blocked -> blocked.getBlockedCharacterId().equals(ownedCharacter.getCharacterId()));
    }

    public List<SkyXpCharacter> getCharactersByUserId(String userId) {
        return characterDao.findByUserId(userId);
    }

    public View<EquipmentViewList> getEquipmentsOfCharacter(String characterId) {
        SkyXpCharacter character = findByCharacterId(characterId);

        return new View<>(
            new EquipmentViewList(character.getEquipments()),
            gameDataFacade.collectEquipmentData(character.getEquipments())
        );
    }

    public Integer getMoneyOfCharacter(String characterId) {
        SkyXpCharacter character = findByCharacterId(characterId);
        return character.getMoney();
    }

    public boolean isCharNameExists(String charName) {
        return characterDao.findByCharacterName(charName) != null;
    }
}
