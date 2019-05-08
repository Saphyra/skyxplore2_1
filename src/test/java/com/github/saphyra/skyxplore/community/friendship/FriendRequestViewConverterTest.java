package com.github.saphyra.skyxplore.community.friendship;

import com.github.saphyra.skyxplore.character.CharacterQueryService;
import com.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.community.friendship.domain.FriendRequest;
import com.github.saphyra.skyxplore.community.friendship.domain.FriendRequestView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FriendRequestViewConverterTest {
    private static final String FRIEND_ID = "friend_id";
    private static final String FRIEND_NAME = "friend_name";
    private static final String CHARACTER_ID = "character_id";
    private static final String FRIEND_REQUEST_ID = "friend_request_id";
    @Mock
    private CharacterQueryService characterQueryService;

    @InjectMocks
    private FriendRequestViewConverter underTest;

    @Test
    public void testConvertDomainShouldConvert() {
        //GIVEN
        FriendRequest friendRequest = FriendRequest.builder()
            .friendId(FRIEND_ID)
            .characterId(CHARACTER_ID)
            .friendRequestId(FRIEND_REQUEST_ID)
            .build();

        SkyXpCharacter character = SkyXpCharacter.builder()
            .characterName(FRIEND_NAME)
            .build();

        when(characterQueryService.findByCharacterId(FRIEND_ID)).thenReturn(character);
        //WHEN
        FriendRequestView result = underTest.convertDomain(friendRequest);
        //THEN
        verify(characterQueryService).findByCharacterId(FRIEND_ID);
        assertThat(result.getCharacterId()).isEqualTo(CHARACTER_ID);
        assertThat(result.getFriendRequestId()).isEqualTo(FRIEND_REQUEST_ID);
        assertThat(result.getFriendId()).isEqualTo(FRIEND_ID);
        assertThat(result.getFriendName()).isEqualTo(FRIEND_NAME);
    }
}
