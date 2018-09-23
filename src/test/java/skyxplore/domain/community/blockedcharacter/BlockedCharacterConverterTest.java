package skyxplore.domain.community.blockedcharacter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static skyxplore.testutil.TestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class BlockedCharacterConverterTest {
    @InjectMocks
    private BlockedCharacterConverter underTest;

    @Test
    public void testConvertEntityShouldReturnNullWhenNull() {
        //GIVEN
        BlockedCharacterEntity entity = null;
        //WHEN
        BlockedCharacter result = underTest.convertEntity(entity);
        //THEN
        assertNull(result);
    }

    @Test
    public void testConvertEntityShouldConvert() {
        //GIVEN
        BlockedCharacterEntity entity = createBlockedCharacterEntity();
        //WHEN
        BlockedCharacter result = underTest.convertEntity(entity);
        //THEN
        assertEquals(BLOCKED_CHARACTER_ENTITY_ID, result.getBlockedCharacterEntityId());
        assertEquals(BLOCKED_CHARACTER_ID, result.getBlockedCharacterId());
        assertEquals(CHARACTER_ID_1, result.getCharacterId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertDomainShouldThrowExceptionWhenNull() {
        //GIVEN
        BlockedCharacter character = null;
        //WHEN
        underTest.convertDomain(character);
    }

    @Test
    public void testConvertDomainShouldConvert() {
        //GIVEN
        BlockedCharacter character = createBlockedCharacter();
        //WHEN
        BlockedCharacterEntity result = underTest.convertDomain(character);
        //THEN
        assertEquals(BLOCKED_CHARACTER_ENTITY_ID, result.getBlockedCharacterEntityId());
        assertEquals(BLOCKED_CHARACTER_ID, result.getBlockedCharacterId());
        assertEquals(CHARACTER_ID_1, result.getCharacterId());
    }
}
