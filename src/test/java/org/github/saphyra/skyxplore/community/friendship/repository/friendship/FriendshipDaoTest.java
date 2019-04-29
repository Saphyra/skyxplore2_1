package org.github.saphyra.skyxplore.community.friendship.repository.friendship;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.github.saphyra.skyxplore.community.friendship.domain.Friendship;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FriendshipDaoTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String FRIEND_ID = "friend_id";
    private static final String FRIENDSHIP_ID = "friendship_id";
    @Mock
    private FriendshipConverter friendshipConverter;

    @Mock
    private FriendshipRepository friendshipRepository;

    @InjectMocks
    private FriendshipDao underTest;

    @Test
    public void testDeleteShouldCallRepository() {
        //GIVEN
        Friendship friendship = createFriendship();

        FriendshipEntity entity = createFriendshipEntity();
        when(friendshipConverter.convertDomain(friendship)).thenReturn(entity);
        //WHEN
        underTest.delete(friendship);
        //THEN
        verify(friendshipConverter).convertDomain(friendship);
        verify(friendshipRepository).delete(entity);
    }

    @Test
    public void testDeleteByCharacterIdShouldCallRepository() {
        //WHEN
        underTest.deleteByCharacterId(CHARACTER_ID);
        //THEN
        verify(friendshipRepository).deleteByCharacterId(CHARACTER_ID);
    }

    @Test
    public void testGetByCharacterIdOrFriendIdShouldCallRepositoryAndReturnDomain() {
        //GIVEN
        FriendshipEntity entity = createFriendshipEntity();
        List<FriendshipEntity> entityList = Arrays.asList(entity);
        when(friendshipRepository.getByCharacterIdOrFriendId(CHARACTER_ID, FRIEND_ID)).thenReturn(entityList);

        Friendship friendship = createFriendship();
        List<Friendship> friendshipList = Arrays.asList(friendship);
        when(friendshipConverter.convertEntity(entityList)).thenReturn(friendshipList);
        //WHEN
        List<Friendship> result = underTest.getByCharacterIdOrFriendId(CHARACTER_ID, FRIEND_ID);
        //THEN
        verify(friendshipRepository).getByCharacterIdOrFriendId(CHARACTER_ID, FRIEND_ID);
        verify(friendshipConverter).convertEntity(entityList);
        assertThat(result).isEqualTo(friendshipList);
    }

    @Test
    public void testGetByFriendshipIdShouldReturnNull() {
        //GIVEN
        when(friendshipRepository.findById(FRIENDSHIP_ID)).thenReturn(Optional.empty());
        //WHEN
        Friendship result = underTest.getByFriendshipId(FRIENDSHIP_ID);
        //THEN
        verify(friendshipRepository).findById(FRIENDSHIP_ID);
        assertThat(result).isNull();
    }

    @Test
    public void testGetByFriendshipIdShouldCallRepositoryAndReturnDomain() {
        //GIVEN
        FriendshipEntity entity = createFriendshipEntity();
        when(friendshipRepository.findById(FRIENDSHIP_ID)).thenReturn(Optional.of(entity));

        Friendship friendship = createFriendship();
        when(friendshipConverter.convertEntity(entity)).thenReturn(friendship);
        //WHEN
        Friendship result = underTest.getByFriendshipId(FRIENDSHIP_ID);
        //THEN
        verify(friendshipRepository).findById(FRIENDSHIP_ID);
        verify(friendshipConverter).convertEntity(entity);
        assertThat(result).isEqualTo(friendship);
    }

    @Test
    public void testGetFriendshipsOfCharacterShouldCallRepositoryAndReturnDomain() {
        //GIVEN
        FriendshipEntity entity = createFriendshipEntity();
        List<FriendshipEntity> entityList = Arrays.asList(entity);
        when(friendshipRepository.getFriendshipsOfCharacter(CHARACTER_ID)).thenReturn(entityList);

        Friendship friendship = createFriendship();
        List<Friendship> friendshipList = Arrays.asList(friendship);
        when(friendshipConverter.convertEntity(entityList)).thenReturn(friendshipList);
        //WHEN
        List<Friendship> result = underTest.getFriendshipsOfCharacter(CHARACTER_ID);
        //THEN
        verify(friendshipRepository).getFriendshipsOfCharacter(CHARACTER_ID);
        verify(friendshipConverter).convertEntity(entityList);
        assertThat(result).isEqualTo(friendshipList);
    }

    @Test
    public void testSaveShouldCallRepository() {
        //GIVEN
        Friendship friendship = createFriendship();

        FriendshipEntity entity = createFriendshipEntity();
        when(friendshipConverter.convertDomain(friendship)).thenReturn(entity);
        //WHEN
        underTest.save(friendship);
        //THEN
        verify(friendshipConverter).convertDomain(friendship);
        verify(friendshipRepository).save(entity);
    }

    private Friendship createFriendship() {
        return Friendship.builder()
            .friendId(FRIEND_ID)
            .friendshipId(FRIENDSHIP_ID)
            .characterId(CHARACTER_ID)
            .build();
    }

    private FriendshipEntity createFriendshipEntity() {
        return FriendshipEntity.builder()
            .friendId(FRIEND_ID)
            .friendshipId(FRIENDSHIP_ID)
            .characterId(CHARACTER_ID)
            .build();
    }
}
