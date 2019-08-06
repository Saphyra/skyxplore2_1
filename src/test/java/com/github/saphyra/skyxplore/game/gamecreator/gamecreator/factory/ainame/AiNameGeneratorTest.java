package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.ainame;

import com.github.saphyra.util.Random;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class AiNameGeneratorTest {
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";

    @Mock
    private Random random;

    private AiNameGenerator underTest;

    @Before
    public void setUp() {
        FirstNames firstNames = new FirstNames();
        firstNames.add(FIRST_NAME);
        firstNames.add(FIRST_NAME);

        LastNames lastNames = new LastNames();
        lastNames.add(LAST_NAME);
        lastNames.add(LAST_NAME);

        underTest = new AiNameGenerator(firstNames, lastNames, random);
    }

    @Test
    public void generateCharacterName() {
        //GIVEN
        given(random.randInt(0, 1)).willReturn(0);
        //WHEN
        String result = underTest.generateCharacterName();
        //THEN
        assertThat(result).startsWith(LAST_NAME);
        assertThat(result).endsWith(FIRST_NAME);
    }
}