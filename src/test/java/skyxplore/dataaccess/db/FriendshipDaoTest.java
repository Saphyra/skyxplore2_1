package skyxplore.dataaccess.db;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.dataaccess.db.repository.FriendshipRepository;
import skyxplore.domain.community.friendship.Friendship;
import skyxplore.domain.community.friendship.FriendshipConverter;
import skyxplore.domain.community.friendship.FriendshipEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.*;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
@RunWith(MockitoJUnitRunner.class)
public class FriendshipDaoTest {
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
        assertEquals(friendshipList, result);
    }

    @Test
    public void testGetByFriendshipIdShouldReturnNull() {
        //GIVEN
        when(friendshipRepository.findById(FRIENDSHIP_ID)).thenReturn(Optional.empty());
        //WHEN
        Friendship result = underTest.getByFriendshipId(FRIENDSHIP_ID);
        //THEN
        verify(friendshipRepository).findById(FRIENDSHIP_ID);
        assertNull(result);
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
        assertEquals(friendship, result);
    }

    @Test
    public void testGetFriendshipsOfCharacterShouldCallRepositoryAndReturnDomain(){
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
        assertEquals(friendshipList, result);
    }

    @Test
    public void testSaveShouldCallRepository(){
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
}
