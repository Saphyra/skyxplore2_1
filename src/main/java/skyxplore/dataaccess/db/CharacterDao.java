package skyxplore.dataaccess.db;

import com.github.saphyra.dao.AbstractDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.repository.CharacterRepository;
import skyxplore.domain.character.CharacterConverter;
import skyxplore.domain.character.CharacterEntity;
import skyxplore.domain.character.SkyXpCharacter;

import java.util.List;

@Component
@Slf4j
public class CharacterDao extends AbstractDao<CharacterEntity, SkyXpCharacter, String, CharacterRepository> {
    private final BlockedCharacterDao blockedCharacterDao;
    private final EquippedShipDao equippedShipDao;
    private final FactoryDao factoryDao;
    private final FriendRequestDao friendRequestDao;
    private final FriendshipDao friendshipDao;
    private final MailDao mailDao;

    public CharacterDao(
        CharacterConverter converter,
        CharacterRepository repository,
        BlockedCharacterDao blockedCharacterDao,
        EquippedShipDao equippedShipDao,
        FactoryDao factoryDao,
        FriendRequestDao friendRequestDao,
        FriendshipDao friendshipDao,
        MailDao mailDao
    ) {
        super(converter, repository);
        this.blockedCharacterDao = blockedCharacterDao;
        this.equippedShipDao = equippedShipDao;
        this.factoryDao = factoryDao;
        this.friendRequestDao = friendRequestDao;
        this.friendshipDao = friendshipDao;
        this.mailDao = mailDao;
    }

    @Override
    public void deleteById(String characterId) {
        equippedShipDao.deleteByCharacterId(characterId);
        factoryDao.deleteByCharacterId(characterId);
        friendRequestDao.deleteByCharacterId(characterId);
        friendshipDao.deleteByCharacterId(characterId);
        blockedCharacterDao.deleteByCharacterId(characterId);
        mailDao.deleteByCharacterId(characterId);

        log.info("Deleting character {}", characterId);
        super.deleteById(characterId);
    }

    public void deleteByUserId(String userId) {
        List<SkyXpCharacter> characters = findByUserId(userId);
        characters.forEach(e -> deleteById(e.getCharacterId()));
    }

    public SkyXpCharacter findByCharacterName(String characterName) {
        return converter.convertEntity(repository.findByCharacterName(characterName));
    }

    public List<SkyXpCharacter> findCharacterByNameLike(String name) {
        return converter.convertEntity(repository.findByCharacterNameContaining(name));
    }


    public List<SkyXpCharacter> findByUserId(String userId) {
        return converter.convertEntity(repository.findByUserId(userId));
    }
}
