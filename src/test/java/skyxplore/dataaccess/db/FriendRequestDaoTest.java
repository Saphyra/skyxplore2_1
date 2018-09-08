package skyxplore.dataaccess.db;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.dataaccess.db.repository.FriendRequestRepository;
import skyxplore.domain.community.friendrequest.FriendRequest;
import skyxplore.domain.community.friendrequest.FriendRequestConverter;
import skyxplore.domain.community.friendrequest.FriendRequestEntity;

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
public class FriendRequestDaoTest {
    @Mock
    private FriendRequestConverter friendRequestConverter;

    @Mock
    private FriendRequestRepository friendRequestRepository;

    @InjectMocks
    private FriendRequestDao underTest;

    @Test
    public void testDeleteShouldCallRepository(){
        //GIVEN
        FriendRequest request = createFriendRequest();

        FriendRequestEntity entity = createFriendRequestEntity();
        when(friendRequestConverter.convertDomain(request)).thenReturn(entity);
        //WHEN
        underTest.delete(request);
        //THEN
        verify(friendRequestConverter).convertDomain(request);
        verify(friendRequestRepository).delete(entity);
    }

    @Test
    public void testDeleteByCharacterIdShouldCallRepository(){
        //WHEN
        underTest.deleteByCharacterId(CHARACTER_ID);
        //THEN
        verify(friendRequestRepository).deleteByCharacterId(CHARACTER_ID);
    }

    @Test
    public void testFindByIdShouldReturnNull(){
        //GIVEN
        when(friendRequestRepository.findById(FRIEND_REQUEST_ID)).thenReturn(Optional.empty());
        //WHEN
        FriendRequest result = underTest.findById(FRIEND_REQUEST_ID);
        //THEN
        assertNull(result);
    }

    @Test
    public void testFindByIdShouldCallRepositoryAndReturnDomain(){
        //GIVEN
        FriendRequestEntity entity = createFriendRequestEntity();
        when(friendRequestRepository.findById(FRIEND_REQUEST_ID)).thenReturn(Optional.of(entity));

        FriendRequest request = createFriendRequest();
        when(friendRequestConverter.convertEntity(entity)).thenReturn(request);
        //WHEN
        FriendRequest result = underTest.findById(FRIEND_REQUEST_ID);
        //THEN
        verify(friendRequestRepository).findById(FRIEND_REQUEST_ID);
        verify(friendRequestConverter).convertEntity(entity);
        assertEquals(request, result);
    }

    @Test
    public void testGetByCharacterIdShouldCallRepositoryAndReturnDomain(){
        //GIVEN
        FriendRequestEntity entity = createFriendRequestEntity();
        List<FriendRequestEntity> entityList = Arrays.asList(entity);
        when(friendRequestRepository.findByCharacterId(CHARACTER_ID)).thenReturn(entityList);

        FriendRequest request = createFriendRequest();
        List<FriendRequest> requestList = Arrays.asList(request);
        when(friendRequestConverter.convertEntity(entityList)).thenReturn(requestList);
        //WHEN
        List<FriendRequest> result = underTest.getByCharacterId(CHARACTER_ID);
        //THEN
        verify(friendRequestRepository).findByCharacterId(CHARACTER_ID);
        verify(friendRequestConverter).convertEntity(entityList);
        assertEquals(requestList, result);
    }

    @Test
    public void testGetByCharacterIdOrFriendIdShouldCallRepositoryAndReturnDomain(){
        //GIVEN
        FriendRequestEntity entity = createFriendRequestEntity();
        List<FriendRequestEntity> entityList = Arrays.asList(entity);
        when(friendRequestRepository.findByCharacterIdOrFriendId(CHARACTER_ID, FRIEND_ID)).thenReturn(entityList);

        FriendRequest request = createFriendRequest();
        List<FriendRequest> requestList = Arrays.asList(request);
        when(friendRequestConverter.convertEntity(entityList)).thenReturn(requestList);
        //WHEN
        List<FriendRequest> result = underTest.getByCharacterIdOrFriendId(CHARACTER_ID, FRIEND_ID);
        //THEN
        verify(friendRequestRepository).findByCharacterIdOrFriendId(CHARACTER_ID, FRIEND_ID);
        verify(friendRequestConverter).convertEntity(entityList);
        assertEquals(requestList, result);
    }

    @Test
    public void testGetByFriendIdShouldCallRepositoryAndReturnDomain(){
        //GIVEN
        FriendRequestEntity entity = createFriendRequestEntity();
        List<FriendRequestEntity> entityList = Arrays.asList(entity);
        when(friendRequestRepository.getByFriendId(FRIEND_ID)).thenReturn(entityList);

        FriendRequest request = createFriendRequest();
        List<FriendRequest> requestList = Arrays.asList(request);
        when(friendRequestConverter.convertEntity(entityList)).thenReturn(requestList);
        //WHEN
        List<FriendRequest> result = underTest.getByFriendId(FRIEND_ID);
        //THEN
        verify(friendRequestRepository).getByFriendId(FRIEND_ID);
        verify(friendRequestConverter).convertEntity(entityList);
        assertEquals(requestList, result);
    }

    @Test
    public void testSaveShouldCallRepository(){
        //GIVEN
        FriendRequest request = createFriendRequest();

        FriendRequestEntity entity = createFriendRequestEntity();
        when(friendRequestConverter.convertDomain(request)).thenReturn(entity);
        //WHEN
        underTest.save(request);
        //THEN
        verify(friendRequestConverter).convertDomain(request);
        verify(friendRequestRepository).save(entity);
    }
}
