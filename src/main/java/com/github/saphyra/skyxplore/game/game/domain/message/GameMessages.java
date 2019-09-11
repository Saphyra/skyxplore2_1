package com.github.saphyra.skyxplore.game.game.domain.message;

import static java.util.Objects.isNull;

import java.util.List;
import java.util.UUID;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.saphyra.skyxplore.common.ConcurrentOptionalMap;
import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.common.OptionalMap;
import com.github.saphyra.skyxplore.game.game.GameContext;
import com.github.saphyra.skyxplore.game.game.request.CreateRoomRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
//TODO unit test
public class GameMessages {
    private final GameContext gameContext;

    @NonNull
    private final OptionalMap<UUID, ChatRoom> chatRooms = new ConcurrentOptionalMap<>();

    public void addRoom(UUID roomId, ChatRoom chatRoom) {
        chatRooms.put(roomId, chatRoom);
    }

    public void createRoom(String characterId, CreateRoomRequest createRoomRequest) {
        List<String> members = Stream.of(characterId, createRoomRequest.getInitialMember())
            .filter(s -> !isNull(s))
            .collect(Collectors.toList());

        ChatRoom room = ChatRoom.builder()
            .roomId(gameContext.getIdGenerator().randomUUID())
            .displayName(createRoomRequest.getRoomName())
            .members(new Vector<>(members))
            .build();
        addRoom(room.getRoomId(), room);
    }

    public void exitFromRoom(String characterId, UUID roomId) {
        findRoomByIdValidated(roomId).exitFromRoom(characterId);
    }

    private ChatRoom findRoomByIdValidated(UUID roomId) {
        return chatRooms.getOptional(roomId)
            .orElseThrow(() -> ExceptionFactory.chatRoomNotFound(roomId));
    }

    public List<ChatRoom> getRoomsOfCharacter(String characterId) {
        return chatRooms.values().stream()
            .filter(chatRoom -> chatRoom.isInRoom(characterId))
            .collect(Collectors.toList());
    }

    public void inviteToRoom(String characterId, UUID roomId, String invitedCharacterId) {
        ChatRoom chatRoom = findRoomByIdValidated(roomId);
        if(!chatRoom.isInRoom(characterId)){
            throw ExceptionFactory.invalidChatRoomAccess(characterId, roomId);
        }

        if(chatRoom.isInRoom(invitedCharacterId)){
            throw ExceptionFactory.alreadyInChatRoom(invitedCharacterId, roomId);
        }

        chatRoom.addMember(invitedCharacterId);
    }

    public void sendMessage(String characterId, UUID roomId, String message) {
        //TODO message length validation
        findRoomByIdValidated(roomId).sendMessage(characterId, message, gameContext.getGameMessageFactory());
    }
}
