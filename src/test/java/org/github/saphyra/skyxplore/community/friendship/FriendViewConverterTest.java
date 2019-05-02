package org.github.saphyra.skyxplore.community.friendship;

import org.github.saphyra.skyxplore.auth.domain.SkyXpAccessToken;
import org.github.saphyra.skyxplore.auth.repository.AccessTokenDao;
import org.github.saphyra.skyxplore.character.CharacterQueryService;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.community.friendship.domain.FriendView;
import org.github.saphyra.skyxplore.community.friendship.domain.Friendship;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FriendViewConverterTest {
    private static final String FRIEND_NAME = "friend_name";
    private static final String FRIEND_ID = "friend_id";
    private static final String CHARACTER_ID = "character_id";
    private static final String FRIENDSHIP_ID = "friendship_id";

    @Mock
    private AccessTokenDao accessTokenDao;

    @Mock
    private CharacterQueryService characterQueryService;

    @InjectMocks
    private FriendViewConverter underTest;

    @Test
    public void testConvertDomainShouldConvert() {
        //GIVEN
        Friendship friendship = Friendship.builder()
            .friendshipId(FRIENDSHIP_ID)
            .characterId(CHARACTER_ID)
            .friendId(FRIEND_ID)
            .build();
        List<Friendship> friendshipList = Arrays.asList(friendship);

        SkyXpCharacter friend = SkyXpCharacter.builder().characterName(FRIEND_NAME).build();

        when(characterQueryService.findByCharacterId(FRIEND_ID)).thenReturn(friend);
        when(accessTokenDao.findByCharacterId(FRIEND_ID)).thenReturn(Optional.of(new SkyXpAccessToken()));
        //WHEN
        List<FriendView> result = underTest.convertDomain(friendshipList, CHARACTER_ID);
        //THEN
        verify(characterQueryService).findByCharacterId(FRIEND_ID);
        FriendView view = result.get(0);
        assertThat(view.getFriendshipId()).isEqualTo(FRIENDSHIP_ID);
        assertThat(view.getFriendId()).isEqualTo(FRIEND_ID);
        assertThat(view.getFriendName()).isEqualTo(FRIEND_NAME);
        assertThat(view.getActive()).isTrue();
    }

    @Test
    public void testConvertDomainShouldSwapIdsAndConvert() {
        //GIVEN
        Friendship friendship = Friendship.builder()
            .friendshipId(FRIENDSHIP_ID)
            .friendId(CHARACTER_ID)
            .characterId(FRIEND_ID)
            .build();
        List<Friendship> friendshipList = Arrays.asList(friendship);

        SkyXpCharacter friend = SkyXpCharacter.builder().build();
        friend.setCharacterName(FRIEND_NAME);

        when(characterQueryService.findByCharacterId(FRIEND_ID)).thenReturn(friend);
        when(accessTokenDao.findByCharacterId(FRIEND_ID)).thenReturn(Optional.of(new SkyXpAccessToken()));
        //WHEN
        List<FriendView> result = underTest.convertDomain(friendshipList, CHARACTER_ID);
        //THEN
        verify(characterQueryService).findByCharacterId(FRIEND_ID);
        FriendView view = result.get(0);
        assertThat(view.getFriendshipId()).isEqualTo(FRIENDSHIP_ID);
        assertThat(view.getFriendId()).isEqualTo(FRIEND_ID);
        assertThat(view.getFriendName()).isEqualTo(FRIEND_NAME);
        assertThat(view.getActive()).isTrue();
    }
}
