package org.github.saphyra.skyxplore.community.friendship.repository.friendrequest;

import org.github.saphyra.skyxplore.community.friendship.domain.FriendRequest;
import org.github.saphyra.skyxplore.event.CharacterDeletedEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FriendRequestDaoTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String FRIEND_ID = "friend_id";
    private static final String FRIEND_REQUEST_ID = "friend_request_id";
    @Mock
    private FriendRequestConverter friendRequestConverter;

    @Mock
    private FriendRequestRepository friendRequestRepository;

    @InjectMocks
    private FriendRequestDao underTest;

    @Test
    public void testDeleteShouldCallRepository() {
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
    public void testDeleteByCharacterIdShouldCallRepository() {
        //WHEN
        underTest.characterDeletedEventProcessor(new CharacterDeletedEvent(CHARACTER_ID));
        //THEN
        verify(friendRequestRepository).deleteByCharacterId(CHARACTER_ID);
    }

    @Test
    public void testGetByCharacterIdShouldCallRepositoryAndReturnDomain() {
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
        assertThat(result).isEqualTo(requestList);
    }

    @Test
    public void testGetByCharacterIdOrFriendIdShouldCallRepositoryAndReturnDomain() {
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
        assertThat(result).isEqualTo(requestList);
    }

    @Test
    public void testGetByFriendIdShouldCallRepositoryAndReturnDomain() {
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
        assertThat(result).isEqualTo(requestList);
    }

    @Test
    public void testSaveShouldCallRepository() {
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

    private FriendRequestEntity createFriendRequestEntity() {
        return FriendRequestEntity.builder()
            .characterId(CHARACTER_ID)
            .friendId(FRIEND_ID)
            .friendRequestId(FRIEND_REQUEST_ID)
            .build();
    }

    private FriendRequest createFriendRequest() {
        return  FriendRequest.builder()
            .characterId(CHARACTER_ID)
            .friendId(FRIEND_ID)
            .friendRequestId(FRIEND_REQUEST_ID)
            .build();
    }
}
