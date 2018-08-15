package skyxplore;

import skyxplore.controller.view.character.CharacterView;
import skyxplore.domain.character.SkyXpCharacter;

public class TestUtils {
    public static final String CHARACTER_ID = "character_id";
    public static final String CHARACTER_NAME = "character_name";
    public static final Integer MONEY = 10;
    public static final String USER_ID = "user_id";

    public static SkyXpCharacter createCharacter(){
        SkyXpCharacter character = new SkyXpCharacter();
        character.setCharacterId(CHARACTER_ID);
        character.setCharacterName(CHARACTER_NAME);
        character.setUserId(USER_ID);
        character.addMoney(MONEY);
        return character;
    }

    public static CharacterView createCharacterView(SkyXpCharacter character){
        CharacterView view = new CharacterView();
        view.setCharacterId(character.getCharacterId());
        view.setCharacterName(character.getCharacterName());
        return view;
    }
}
