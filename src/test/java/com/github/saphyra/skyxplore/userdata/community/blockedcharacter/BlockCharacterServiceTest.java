package com.github.saphyra.skyxplore.userdata.community.blockedcharacter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.skyxplore.common.exception.BlockedCharacterNotFoundException;
import com.github.saphyra.skyxplore.common.exception.CharacterAlreadyBlockedException;
import com.github.saphyra.skyxplore.userdata.community.blockedcharacter.domain.BlockedCharacter;
import com.github.saphyra.skyxplore.userdata.community.blockedcharacter.repository.BlockedCharacterDao;
import com.github.saphyra.skyxplore.userdata.community.friendship.FriendshipService;

@RunWith(MockitoJUnitRunner.class)
public class BlockCharacterServiceTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String BLOCKED_CHARACTER_ID = "blocked_character_id";

    @Mock
    private BlockedCharacterDao blockedCharacterDao;

    @Mock
    private BlockedCharacterQueryService blockedCharacterQueryService;

    @Mock
    private FriendshipService friendshipService;

    @InjectMocks
    private BlockCharacterService underTest;

    @Mock
    private BlockedCharacter blockedCharacter;

    @Test(expected = BlockedCharacterNotFoundException.class)
    public void testAllowBlockedCharacterShouldThrowExceptionWhenNotFound() {
        //GIVEN
        when(blockedCharacterQueryService.findByCharacterIdAndBlockedCharacterId(CHARACTER_ID, BLOCKED_CHARACTER_ID)).thenReturn(Optional.empty());
        //WHEN
        underTest.allowBlockedCharacter(BLOCKED_CHARACTER_ID, CHARACTER_ID);
    }

    @Test
    public void testAllowBlockedCharacterShouldDeleteBlock() {
        //GIVEN
        when(blockedCharacterQueryService.findByCharacterIdAndBlockedCharacterId(CHARACTER_ID, BLOCKED_CHARACTER_ID)).thenReturn(Optional.of(blockedCharacter));
        //WHEN
        underTest.allowBlockedCharacter(BLOCKED_CHARACTER_ID, CHARACTER_ID);
        //THEN
        verify(blockedCharacterQueryService).findByCharacterIdAndBlockedCharacterId(CHARACTER_ID, BLOCKED_CHARACTER_ID);
        verify(blockedCharacterDao).delete(blockedCharacter);
    }

    @Test(expected = BadRequestException.class)
    public void testBlockCharacterShouldThrowExceptionWhenIdsEqual() {
        underTest.blockCharacter(CHARACTER_ID, CHARACTER_ID);
    }

    @Test(expected = CharacterAlreadyBlockedException.class)
    public void testBlockCharacterShouldThrowExceptionWhenAlreadyBlocked() {
        //GIVEN
        when(blockedCharacterQueryService.findByCharacterIdAndBlockedCharacterId(CHARACTER_ID, BLOCKED_CHARACTER_ID)).thenReturn(Optional.of(blockedCharacter));
        //WHEN
        underTest.blockCharacter(BLOCKED_CHARACTER_ID, CHARACTER_ID);
    }

    @Test
    public void testBlockCharacterShouldBlock() {
        //GIVEN
        when(blockedCharacterQueryService.findByCharacterIdAndBlockedCharacterId(CHARACTER_ID, BLOCKED_CHARACTER_ID)).thenReturn(Optional.empty());
        //WHEN
        underTest.blockCharacter(BLOCKED_CHARACTER_ID, CHARACTER_ID);
        //THEN
        verify(blockedCharacterQueryService).findByCharacterIdAndBlockedCharacterId(CHARACTER_ID, BLOCKED_CHARACTER_ID);
        verify(friendshipService).removeContactsBetween(CHARACTER_ID, BLOCKED_CHARACTER_ID);

        ArgumentCaptor<BlockedCharacter> argumentCaptor = ArgumentCaptor.forClass(BlockedCharacter.class);
        verify(blockedCharacterDao).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getCharacterId()).isEqualTo(CHARACTER_ID);
        assertThat(argumentCaptor.getValue().getBlockedCharacterId()).isEqualTo(BLOCKED_CHARACTER_ID);
    }
}