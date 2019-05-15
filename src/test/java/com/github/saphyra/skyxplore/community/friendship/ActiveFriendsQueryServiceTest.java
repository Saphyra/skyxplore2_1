package com.github.saphyra.skyxplore.community.friendship;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.auth.domain.SkyXpAccessToken;
import com.github.saphyra.skyxplore.auth.repository.AccessTokenDao;
import com.github.saphyra.skyxplore.character.CharacterQueryService;
import com.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.community.friendship.domain.Friendship;
import com.github.saphyra.skyxplore.lobby.lobby.LobbyQueryService;
import com.github.saphyra.skyxplore.lobby.lobby.domain.Lobby;

@RunWith(MockitoJUnitRunner.class)
public class ActiveFriendsQueryServiceTest {
    private static final String FRIEND_ID_1 = "friend_id_1";
    private static final String FRIEND_ID_2 = "friend_id_2";
    private static final String FRIEND_ID_3 = "friend_id_3";
    private static final String CHARACTER_ID = "character_id";
    @Mock
    private AccessTokenDao accessTokenDao;

    @Mock
    private CharacterQueryService characterQueryService;

    @Mock
    private FriendshipQueryService friendshipQueryService;

    @Mock
    private LobbyQueryService lobbyQueryService;

    @Mock
    private Lobby lobby;

    @InjectMocks
    private ActiveFriendsQueryService underTest;

    @Test
    public void getActiveFriends() {
        //GIVEN
        Friendship inactiveFriendship = Friendship.builder()
            .characterId(CHARACTER_ID)
            .friendId(FRIEND_ID_1)
            .build();
        given(accessTokenDao.findByCharacterId(FRIEND_ID_1)).willReturn(Optional.empty());

        Friendship inLobbyFriendship = Friendship.builder()
            .characterId(CHARACTER_ID)
            .friendId(FRIEND_ID_2)
            .build();
        given(accessTokenDao.findByCharacterId(FRIEND_ID_2)).willReturn(Optional.of(SkyXpAccessToken.builder().build()));
        given(lobbyQueryService.findByCharacterId(FRIEND_ID_2)).willReturn(Optional.of(lobby));

        Friendship invitableFriendsip = Friendship.builder()
            .characterId(CHARACTER_ID)
            .friendId(FRIEND_ID_3)
            .build();
        given(accessTokenDao.findByCharacterId(FRIEND_ID_3)).willReturn(Optional.of(SkyXpAccessToken.builder().build()));
        given(lobbyQueryService.findByCharacterId(FRIEND_ID_3)).willReturn(Optional.empty());

        SkyXpCharacter character = SkyXpCharacter.builder().build();
        given(characterQueryService.findByCharacterId(FRIEND_ID_3)).willReturn(character);

        given(friendshipQueryService.getFriends(CHARACTER_ID)).willReturn(Arrays.asList(inactiveFriendship, inLobbyFriendship, invitableFriendsip));
        //WHEN
        List<SkyXpCharacter> result = underTest.getActiveFriends(CHARACTER_ID);
        //THEN
        assertThat(result).containsExactly(character);
    }
}