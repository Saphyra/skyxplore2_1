package com.github.saphyra.skyxplore.community.friendship;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.exceptionhandling.exception.UnauthorizedException;
import com.github.saphyra.skyxplore.character.CharacterQueryService;
import com.github.saphyra.skyxplore.common.exception.CharacterBlockedException;
import com.github.saphyra.skyxplore.common.exception.FriendshipAlreadyExistsException;
import com.github.saphyra.skyxplore.community.blockedcharacter.domain.BlockedCharacter;
import com.github.saphyra.skyxplore.community.friendship.domain.FriendRequest;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.github.saphyra.skyxplore.community.blockedcharacter.BlockedCharacterQueryService;
import com.github.saphyra.skyxplore.community.friendship.domain.Friendship;
import com.github.saphyra.skyxplore.community.friendship.repository.friendrequest.FriendRequestDao;
import com.github.saphyra.skyxplore.community.friendship.repository.friendship.FriendshipDao;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FriendshipService {
    private final BlockedCharacterQueryService blockedCharacterQueryService;
    private final CharacterQueryService characterQueryService;
    private final FriendRequestDao friendRequestDao;
    private final FriendshipDao friendshipDao;
    private final FriendshipQueryService friendshipQueryService;
    private final IdGenerator idGenerator;

    @Transactional
    public void acceptFriendRequest(String friendRequestId, String characterId) {
        FriendRequest friendRequest = friendshipQueryService.findFriendRequestById(friendRequestId);
        if (!friendRequest.getFriendId().equals(characterId)) {
            throw new UnauthorizedException(characterId + "has no rights to accept friendRequest " + friendRequestId);
        }

        Friendship friendship = Friendship.builder()
            .friendshipId(idGenerator.generateRandomId())
            .characterId(friendRequest.getCharacterId())
            .friendId(friendRequest.getFriendId())
            .build();
        friendshipDao.save(friendship);
        friendRequestDao.delete(friendRequest);
    }

    public void addFriendRequest(String friendId, String characterId, String userId) {
        //Check if character with the given characterId exists
        characterQueryService.findByCharacterId(friendId);
        List<BlockedCharacter> blockedCharacter = blockedCharacterQueryService.findByCharacterIdOrBlockedCharacterId(characterId, friendId);

        if (!blockedCharacter.isEmpty()) {
            throw new CharacterBlockedException(friendId + " is blocked.");
        }
        if (friendshipQueryService.isFriendshipOrFriendRequestAlreadyExists(characterId, friendId)) {
            throw new FriendshipAlreadyExistsException(friendId, characterId);
        }
        if (isOwnCharacter(friendId, userId)) {
            throw new BadRequestException("You cannot add your user's characters as friend.");
        }

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setFriendRequestId(idGenerator.generateRandomId());
        friendRequest.setCharacterId(characterId);
        friendRequest.setFriendId(friendId);

        friendRequestDao.save(friendRequest);
    }

    private boolean isOwnCharacter(String characterId, String userId) {
        return characterQueryService.getCharactersByUserId(userId).stream()
            .anyMatch(skyXpCharacter -> skyXpCharacter.getCharacterId().equals(characterId));
    }

    public void declineFriendRequest(String friendRequestId, String characterId) {
        FriendRequest friendRequest = friendshipQueryService.findFriendRequestById(friendRequestId);
        if (!friendRequest.getCharacterId().equals(characterId)
            && !friendRequest.getFriendId().equals(characterId)) {
            throw new UnauthorizedException(friendRequestId + " is not a friendRequest of character " + characterId);
        }
        friendRequestDao.delete(friendRequest);
    }

    public void deleteFriendship(String friendshipId, String characterId) {
        Friendship friendship = friendshipQueryService.findFriendshipById(friendshipId);
        if (!friendship.getFriendId().equals(characterId)
            && !friendship.getCharacterId().equals(characterId)) {
            throw new UnauthorizedException(characterId + " has no access to friendship " + friendshipId);
        }
        friendshipDao.delete(friendship);
    }

    public void removeContactsBetween(String characterId, String blockedCharacterId) {
        friendRequestDao.getByCharacterIdOrFriendId(characterId, blockedCharacterId).forEach(friendRequestDao::delete);
        friendshipDao.getByCharacterIdOrFriendId(characterId, blockedCharacterId).forEach(friendshipDao::delete);
    }
}
