package com.github.saphyra.skyxplore.userdata.character;

import com.github.saphyra.util.IdGenerator;
import com.github.saphyra.skyxplore.userdata.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.userdata.character.repository.CharacterDao;
import com.github.saphyra.skyxplore.userdata.factory.FactoryFacade;
import com.github.saphyra.skyxplore.userdata.ship.EquippedShipFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class NewCharacterGeneratorTest {
    private static final String USER_ID = "user_id";
    private static final String CHARACTER_NAME = "character_name";
    private static final String CHARACTER_ID = "character_id";
    private static final Integer START_MONEY = 561;

    @Mock
    private CharacterDao characterDao;

    @Mock
    private CharacterConfig config;

    @Mock
    private EquippedShipFacade equippedShipFacade;

    @Mock
    private FactoryFacade factoryFacade;

    @Mock
    private IdGenerator idGenerator;

    @InjectMocks
    private NewCharacterGenerator underTest;

    @Test
    public void createCharacter() {
        //GIVEN
        given(idGenerator.generateRandomId()).willReturn(CHARACTER_ID);
        given(config.getStartMoney()).willReturn(START_MONEY);
        //WHEN
        SkyXpCharacter result = underTest.createCharacter(USER_ID, CHARACTER_NAME);
        //THEN
        verify(characterDao).save(result);
        verify(factoryFacade).createFactory(CHARACTER_ID);
        verify(equippedShipFacade).createShip(CHARACTER_ID);

        assertThat(result.getCharacterId()).isEqualTo(CHARACTER_ID);
        assertThat(result.getUserId()).isEqualTo(USER_ID);
        assertThat(result.getCharacterName()).isEqualTo(CHARACTER_NAME);
        assertThat(result.getMoney()).isEqualTo(START_MONEY);
        assertThat(result.getEquipments()).isEmpty();
    }
}