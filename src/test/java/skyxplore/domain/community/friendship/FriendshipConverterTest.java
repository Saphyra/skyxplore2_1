package skyxplore.domain.community.friendship;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static skyxplore.testutil.TestUtils.CHARACTER_ID;
import static skyxplore.testutil.TestUtils.FRIENDSHIP_ID;
import static skyxplore.testutil.TestUtils.FRIEND_ID;
import static skyxplore.testutil.TestUtils.createFriendship;
import static skyxplore.testutil.TestUtils.createFriendshipEntity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FriendshipConverterTest {
    @InjectMocks
    private FriendshipConverter underTest;

    @Test
    public void testConvertEntityShouldReturnNullWhenNull(){
        //GIVEN
        FriendshipEntity entity = null;
        //WHEN
        Friendship result = underTest.convertEntity(entity);
        //THEN
        assertNull(result);
    }

    @Test
    public void testConvertEntityShouldConvert(){
        //GIVEN
        FriendshipEntity entity = createFriendshipEntity();
        //WHEN
        Friendship result = underTest.convertEntity(entity);
        //THEN
        assertEquals(FRIENDSHIP_ID, result.getFriendshipId());
        assertEquals(CHARACTER_ID, result.getCharacterId());
        assertEquals(FRIEND_ID, result.getFriendId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertDomainShouldThrowExceptionWhenNull(){
        //GIVEN
        Friendship friendship = null;
        //WHEN
        underTest.convertDomain(friendship);
    }

    @Test
    public void testConvertDomainShouldConvert(){
        //GIVEN
        Friendship friendship = createFriendship();
        //WHEN
        FriendshipEntity result = underTest.convertDomain(friendship);
        //THEN
        assertEquals(FRIENDSHIP_ID, result.getFriendshipId());
        assertEquals(CHARACTER_ID, result.getCharacterId());
        assertEquals(FRIEND_ID, result.getFriendId());
    }
}