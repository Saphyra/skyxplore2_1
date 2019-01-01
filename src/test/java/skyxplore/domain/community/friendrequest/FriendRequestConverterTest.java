package skyxplore.domain.community.friendrequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static skyxplore.testutil.TestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class FriendRequestConverterTest {
    @InjectMocks
    private FriendRequestConverter underTest;

    @Test
    public void testConvertEntityShouldReturnNull(){
        //GIVEN
        FriendRequestEntity entity = null;
        //WHEN
        FriendRequest result = underTest.convertEntity(entity);
        //THEN
        assertNull(result);
    }

    @Test
    public void testConvertEntityShouldConvert(){
        //GIVEN
        FriendRequestEntity entity = createFriendRequestEntity();
        //WHEN
        FriendRequest result = underTest.convertEntity(entity);
        //THEN
        assertEquals(FRIEND_REQUEST_ID, result.getFriendRequestId());
        assertEquals(FRIEND_ID, result.getFriendId());
        assertEquals(CHARACTER_ID_1, result.getCharacterId());
    }

    @Test
    public void testConvertDomainShouldConvert(){
        //GIVEN
        FriendRequest request = createFriendRequest();
        //WHEN
        FriendRequestEntity result = underTest.convertDomain(request);
        //THEN
        assertEquals(FRIEND_REQUEST_ID, result.getFriendRequestId());
        assertEquals(FRIEND_ID, result.getFriendId());
        assertEquals(CHARACTER_ID_1, result.getCharacterId());
    }
}
