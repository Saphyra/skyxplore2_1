package com.github.saphyra.skyxplore.common;

import com.github.saphyra.exceptionhandling.domain.ErrorMessage;
import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.exceptionhandling.exception.ConflictException;
import com.github.saphyra.exceptionhandling.exception.ForbiddenException;
import com.github.saphyra.exceptionhandling.exception.LockedException;
import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.exceptionhandling.exception.PaymentRequiredException;
import com.github.saphyra.exceptionhandling.exception.RestException;
import com.github.saphyra.exceptionhandling.exception.UnauthorizedException;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public final class ExceptionFactory {
    private static final String ALREADY_IN_CHAT_ROOM_PREFIX = "%s is already in ChatRoom %s";
    private static final String BLOCKED_CHARACTER_NOT_FOUND_PREFIX = "BlockedCharacter not found for characterId %s and blockedCharacterId %s";
    private static final String CHARACTER_ALREADY_BLOCKED_PREFIX = "%s is already blocked by %s";
    private static final String CHARACTER_BLOCKED_PREFIX = "There is a block between characters %s and %s";
    private static final String CHARACTER_NAME_ALREADY_EXISTS_PREFIX = "Character already exists with name %s";
    private static final String CHARACTER_NOT_FOUND_PREFIX = "SkyXpCharacter not found with id %s";
    private static final String CHAT_ROOM_NOT_FOUND_PREFIX = "ChatRoom not found with roomId %s";
    private static final String CHARACTER_NOT_IN_GAME_PREFIX = "%s is not in Game %s";
    private static final String CHARACTER_NOT_IN_TEAM_PREFIX = "%s is not in Team %s";
    private static final String CREDENTIALS_NOT_FOUND_PREFIX = "SkyXpCredentials not found with userId %s";
    private static final String EMAIL_ALREADY_EXISTS_PREFIX = "Email %s is already exists.";
    private static final String EQUIPMENT_NOT_FOUND_PREFIX = "Equipment not found with id %s";
    private static final String EQUIPPED_SLOT_NOT_FOUND_PREFIX = "EquippedSlot not found with slotId %s";
    private static final String FACTORY_NOT_FOUND_BY_ID_PREFIX = "Factory not found with id %s";
    private static final String FACTORY_NOT_FOUND_FOR_CHARACTER_PREFIX = "Factory not found with characterId %s";
    private static final String FRIEND_REQUEST_NOT_FOUND_PREFIX = "FriendRequest not found with id %s";
    private static final String FRIENDSHIP_ALREADY_EXISTS_PREFIX = "Friendship already exists between %s and %s";
    private static final String FRIENDSHIP_NOT_FOUND_PREFIX = "Friendship not found with id %s";
    private static final String GAME_NOT_FOUND_PREFIX = "Game not found for character %s";
    private static final String INVALID_CHARACTER_ACCESS_PREFIX = "%s cannot access character %s";
    private static final String INVALID_CHAT_ROOM_ACCESS_PREFIX = "%s has no access to ChatRoom %s";
    private static final String INVALID_FRIEND_REQUEST_ACCESS_PREFIX = "%s cannot access FriendRequest %s";
    private static final String INVALID_FRIENDSHIP_ACCESS_PREFIX = "%s has no access to Friendship %s";
    private static final String INVALID_LOCALE_PREFIX = "Locale %s is not supported.";
    private static final String INVALID_MAIL_ACCESS_PREFIX = "%s cannot access to mail %s";
    private static final String INVALID_SLOT_NAME_PREFIX = "Invalid slotName: %s";
    private static final String LOBBY_NOT_FOUND_PREFIX = "Lobby not found with characterId %s";
    private static final String MAIL_NOT_FOUND_PREFIX = "Mail not found with id %s";
    private static final String NOT_ENOUGH_MATERIALS_PREFIX = "Not enough %s. Required: %s, present: %s";
    private static final String NOT_ENOUGH_MONEY_PREFIX = "%s tried to spend %s money, but only has %s";
    private static final String SHIP_NOT_FOUND_PREFIX = "EquippedShip not found with characterId %s";
    private static final String USER_NAME_ALREADY_EXISTS_PREFIX = "User with name %s already exists.";
    private static final String USER_NOT_FOUND_PREFIX = "User not found with id %s";

    public static RestException alreadyInChatRoom(String invitedCharacterId, UUID roomId) {
        return new ConflictException(createErrorMessage(ErrorCode.ALREADY_IN_CHAT_ROOM), String.format(ALREADY_IN_CHAT_ROOM_PREFIX, invitedCharacterId, roomId.toString()));
    }

    public static RestException blockedCharacterNotFound(String characterId, String blockedCharacterId) {
        throw new NotFoundException(createErrorMessage(ErrorCode.BLOCKED_CHARACTER_NOT_FOUND), String.format(BLOCKED_CHARACTER_NOT_FOUND_PREFIX, characterId, blockedCharacterId));
    }

    public static RestException characterAlreadyBlocked(String characterId, String blockedCharacterId) {
        return new ConflictException(createErrorMessage(ErrorCode.CHARACTER_ALREADY_BLOCKED), String.format(CHARACTER_ALREADY_BLOCKED_PREFIX, blockedCharacterId, characterId));
    }

    public static RestException characterBlockedException(String characterId, String addresseeId) {
        return new ForbiddenException(createErrorMessage(ErrorCode.CHARACTER_BLOCKED), String.format(CHARACTER_BLOCKED_PREFIX, characterId, addresseeId));
    }

    public static RestException characterNameAlreadyExists(String characterName) {
        return new LockedException(createErrorMessage(ErrorCode.CHARACTER_NAME_ALREADY_EXISTS), String.format(CHARACTER_NAME_ALREADY_EXISTS_PREFIX, characterName));
    }

    public static RestException characterNotFound(String characterId) {
        return new NotFoundException(createErrorMessage(ErrorCode.CHARACTER_NOT_FOUND), String.format(CHARACTER_NOT_FOUND_PREFIX, characterId));
    }

    public static ServerException characterNotInGame(String characterId, UUID gameId) {
        return new ServerException(String.format(CHARACTER_NOT_IN_GAME_PREFIX, characterId, gameId.toString()));
    }

    public static ServerException characterNotInTeam(String characterId, UUID teamId) {
        return new ServerException(String.format(CHARACTER_NOT_IN_TEAM_PREFIX, characterId, teamId.toString()));
    }

    public static RestException chatRoomNotFound(UUID roomId) {
        return new NotFoundException(createErrorMessage(ErrorCode.CHAT_ROOM_NOT_FOUND), String.format(CHAT_ROOM_NOT_FOUND_PREFIX, roomId.toString()));
    }

    public static RestException credentialsNotFound(String userId) {
        return new NotFoundException(createErrorMessage(ErrorCode.CREDENTIALS_NOT_FOUND), String.format(CREDENTIALS_NOT_FOUND_PREFIX, userId));
    }

    public static RestException emailAlreadyExists(String newEmail) {
        return new LockedException(createErrorMessage(ErrorCode.EMAIL_ALREADY_EXISTS), String.format(EMAIL_ALREADY_EXISTS_PREFIX, newEmail));
    }

    public static RestException equipmentNotFound(String id) {
        return new NotFoundException(createErrorMessage(ErrorCode.EQUIPMENT_NOT_FOUND), String.format(EQUIPMENT_NOT_FOUND_PREFIX, id));
    }

    public static ServerException equippedSlotNotFound(String slotId) {
        return new ServerException(String.format(EQUIPPED_SLOT_NOT_FOUND_PREFIX, slotId));
    }

    public static ServerException factoryNotFoundById(String factoryId) {
        return new ServerException(String.format(FACTORY_NOT_FOUND_BY_ID_PREFIX, factoryId));
    }

    public static ServerException factoryNotFoundForCharacter(String characterId) {
        return new ServerException(String.format(FACTORY_NOT_FOUND_FOR_CHARACTER_PREFIX, characterId));
    }

    public static RestException friendRequestNotFound(String friendRequestId) {
        return new NotFoundException(createErrorMessage(ErrorCode.FRIEND_REQUEST_NOT_FOUND), String.format(FRIEND_REQUEST_NOT_FOUND_PREFIX, friendRequestId));
    }

    public static RestException friendshipAlreadyExists(String characterId, String friendId) {
        return new ConflictException(createErrorMessage(ErrorCode.FRIENDSHIP_ALREADY_EXISTS), String.format(FRIENDSHIP_ALREADY_EXISTS_PREFIX, characterId, friendId));
    }

    public static RestException friendshipNotFound(String friendshipId) {
        return new NotFoundException(createErrorMessage(ErrorCode.FRIENDSHIP_NOT_FOUND), String.format(FRIENDSHIP_NOT_FOUND_PREFIX, friendshipId));
    }

    public static RestException gameNotFound(String characterId) {
        return new NotFoundException(createErrorMessage(ErrorCode.GAME_NOT_FOUND), String.format(GAME_NOT_FOUND_PREFIX, characterId));
    }

    public static RestException invalidCharacterAccess(String characterId, String userId) {
        return new ForbiddenException(createErrorMessage(ErrorCode.INVALID_CHARACTER_ACCESS), String.format(INVALID_CHARACTER_ACCESS_PREFIX, userId, characterId));
    }

    public static RestException invalidChatRoomAccess(String characterId, UUID roomId) {
        return new ForbiddenException(createErrorMessage(ErrorCode.INVALID_CHAT_ROOM_ACCESS), String.format(INVALID_CHAT_ROOM_ACCESS_PREFIX, characterId, roomId.toString()));
    }

    public static RestException invalidFriendRequestAccess(String friendRequestId, String characterId) {
        return new ForbiddenException(createErrorMessage(ErrorCode.INVALID_FRIEND_REQUEST_ACCESS), String.format(INVALID_FRIEND_REQUEST_ACCESS_PREFIX, characterId, friendRequestId));
    }

    public static RestException invalidFriendshipAccess(String friendshipId, String characterId) {
        return new ForbiddenException(createErrorMessage(ErrorCode.INVALID_FRIENDSHIP_ACCESS), String.format(INVALID_FRIENDSHIP_ACCESS_PREFIX, characterId, friendshipId));
    }

    public static RestException invalidLocale(String locale) {
        return new BadRequestException(createErrorMessage(ErrorCode.INVALID_LOCALE), String.format(INVALID_LOCALE_PREFIX, locale));
    }

    public static RestException invalidMailAccess(String characterId, String mailId) {
        return new ForbiddenException(createErrorMessage(ErrorCode.INVALID_MAIL_ACCESS), String.format(INVALID_MAIL_ACCESS_PREFIX, characterId, mailId));
    }

    public static RestException invalidSlotName(String slotName) {
        throw new BadRequestException(createErrorMessage(ErrorCode.INVALID_SLOT_NAME), String.format(INVALID_SLOT_NAME_PREFIX, slotName));
    }

    public static RestException lobbyNotFound(String characterId) {
        return new NotFoundException(createErrorMessage(ErrorCode.LOBBY_NOT_FOUND), String.format(LOBBY_NOT_FOUND_PREFIX, characterId));
    }

    public static RestException mailNotFound(String mailId) {
        return new NotFoundException(createErrorMessage(ErrorCode.MAIL_NOT_FOUND), String.format(MAIL_NOT_FOUND_PREFIX, mailId));
    }

    public static RestException notEnoughMaterials(String key, Integer amount, Integer actual) {
        return new PaymentRequiredException(createErrorMessage(ErrorCode.NOT_ENOUGH_MATERIALS), String.format(NOT_ENOUGH_MATERIALS_PREFIX, key, amount, actual));
    }

    public static RestException notEnoughMoney(String characterId, Integer moneyToSpend, Integer money) {
        return new PaymentRequiredException(createErrorMessage(ErrorCode.NOT_ENOUGH_MONEY), String.format(NOT_ENOUGH_MONEY_PREFIX, characterId, moneyToSpend, money));
    }

    public static ServerException shipNotFound(String characterId) {
        return new ServerException(String.format(SHIP_NOT_FOUND_PREFIX, characterId));
    }

    public static RestException userNameAlreadyExists(String userName) {
        return new LockedException(createErrorMessage(ErrorCode.USER_NAME_ALREADY_EXISTS), String.format(USER_NAME_ALREADY_EXISTS_PREFIX, userName));
    }

    public static RestException userNotFound(String userId) {
        return new NotFoundException(createErrorMessage(ErrorCode.USER_NOT_FOUND), String.format(USER_NOT_FOUND_PREFIX, userId));
    }

    public static RestException wrongFriendId() {
        return new BadRequestException("You cannot add your user's characters as friend.");
    }

    public static RestException wrongPassword() {
        return new UnauthorizedException(createErrorMessage(ErrorCode.WRONG_PASSWORD), "Wrong password");
    }

    private static ErrorMessage createErrorMessage(ErrorCode errorCode) {
        return new ErrorMessage(errorCode.name());
    }
}
