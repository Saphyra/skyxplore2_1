package com.github.saphyra.skyxplore.userdata.community.friendship.service;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.exceptionhandling.exception.ConflictException;
import com.github.saphyra.exceptionhandling.exception.ForbiddenException;
import com.github.saphyra.skyxplore.common.ErrorCode;
import com.github.saphyra.skyxplore.userdata.character.CharacterQueryService;
import com.github.saphyra.skyxplore.userdata.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.userdata.community.blockedcharacter.BlockedCharacterQueryService;
import com.github.saphyra.skyxplore.userdata.community.blockedcharacter.domain.BlockedCharacter;
import com.github.saphyra.skyxplore.userdata.community.friendship.domain.FriendRequest;
import com.github.saphyra.skyxplore.userdata.community.friendship.repository.friendrequest.FriendRequestDao;
import com.github.saphyra.testing.ExceptionValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AddFriendRequestServiceTest {
    private static final String FRIEND_ID = "friend_id";
    private static final String CHARACTER_ID = "character-id";
    private static final String USER_ID = "user_id";

    @Mock
    private BlockedCharacterQueryService blockedCharacterQueryService;

    @Mock
    private CharacterQueryService characterQueryService;

    @Mock
    private FriendRequestDao friendRequestDao;

    @Mock
    private FriendRequestFactory friendRequestFactory;

    @Mock
    private ContactQueryService contactQueryService;

    @InjectMocks
    private AddFriendRequestService underTest;

    @Mock
    private BlockedCharacter blockedCharacter;

    @Mock
    private SkyXpCharacter character;

    @Mock
    private FriendRequest friendRequest;

    @Before
    public void setUp() {
        given(blockedCharacterQueryService.findByCharacterIdOrBlockedCharacterId(CHARACTER_ID, FRIEND_ID)).willReturn(Collections.emptyList());
        given(characterQueryService.getCharactersByUserId(USER_ID)).willReturn(Arrays.asList(character));
        given(character.getCharacterId()).willReturn(CHARACTER_ID);
        given(contactQueryService.isFriendshipOrFriendRequestAlreadyExists(CHARACTER_ID, FRIEND_ID)).willReturn(false);
    }

    @After
    public void tearDown() {
        verify(characterQueryService).findByCharacterIdValidated(FRIEND_ID);
    }

    @Test
    public void addFriendRequest_blocked() {
        //GIVEN
        given(blockedCharacterQueryService.findByCharacterIdOrBlockedCharacterId(CHARACTER_ID, FRIEND_ID)).willReturn(Arrays.asList(blockedCharacter));
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.addFriendRequest(FRIEND_ID, CHARACTER_ID, USER_ID));
        //THEN
        ExceptionValidator.verifyException(ex, ForbiddenException.class, ErrorCode.CHARACTER_BLOCKED);
    }

    @Test
    public void addFriendRequest_alreadyExists() {
        //GIVEN
        given(contactQueryService.isFriendshipOrFriendRequestAlreadyExists(CHARACTER_ID, FRIEND_ID)).willReturn(true);
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.addFriendRequest(FRIEND_ID, CHARACTER_ID, USER_ID));
        //THEN
        ExceptionValidator.verifyException(ex, ConflictException.class, ErrorCode.FRIENDSHIP_ALREADY_EXISTS);
    }

    @Test(expected = BadRequestException.class)
    public void addFriendRequest_usersCharacter() {
        //GIVEN
        given(character.getCharacterId()).willReturn(FRIEND_ID);
        //WHEN
        underTest.addFriendRequest(FRIEND_ID, CHARACTER_ID, USER_ID);
    }

    @Test
    public void addFriendRequest() {
        //GIVEN
        given(friendRequestFactory.create(CHARACTER_ID, FRIEND_ID)).willReturn(friendRequest);
        //WHEN
        underTest.addFriendRequest(FRIEND_ID, CHARACTER_ID, USER_ID);
        ///THEN
        verify(friendRequestDao).save(friendRequest);
    }
}