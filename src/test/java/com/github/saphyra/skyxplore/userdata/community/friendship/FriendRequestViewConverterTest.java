package com.github.saphyra.skyxplore.userdata.community.friendship;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.userdata.character.CharacterQueryService;
import com.github.saphyra.skyxplore.userdata.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.userdata.community.friendship.domain.FriendRequest;
import com.github.saphyra.skyxplore.userdata.community.friendship.domain.FriendRequestView;

@RunWith(MockitoJUnitRunner.class)
public class FriendRequestViewConverterTest {
    private static final String FRIEND_ID = "friend_id";
    private static final String FRIEND_NAME = "friend_name";
    private static final String CHARACTER_ID = "character_id";
    private static final String FRIEND_REQUEST_ID = "friend_request_id";

    @Mock
    private CharacterQueryService characterQueryService;

    @Mock
    private SkyXpCharacter character;

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

        given(character.getCharacterName()).willReturn(FRIEND_NAME);
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
