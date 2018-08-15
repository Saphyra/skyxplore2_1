package skyxplore;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import skyxplore.controller.view.character.CharacterView;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.domain.product.Product;

public class TestUtils {
    //Character
    public static final String CHARACTER_ID = "character_id";
    public static final String CHARACTER_NAME = "character_name";
    public static final Integer MONEY = 10;

    //Factory
    public static final String FACTORY_ID = "factory_id";

    //Product
    public static final String ELEMENT_ID = "element_id";
    public static final Long PRODUCT_ADDED_AT = 1000L;
    public static final Integer PRODUCT_AMOUNT = 5;
    public static final Integer PRODUCT_CONSTRUCTION_TIME = 100;
    public static final String PRODUCT_ID = "product_id";
    public static final Long PRODUCT_START_TIME_EPOCH = 10000L;
    public static final LocalDateTime PRODUCT_START_TIME = LocalDateTime.ofEpochSecond(PRODUCT_START_TIME_EPOCH, 0, ZoneOffset.UTC);
    public static final Long PRODUC_END_TIME_EPOCH = 20000L;
    public static final LocalDateTime PRODUCT_END_TIME = LocalDateTime.ofEpochSecond(PRODUC_END_TIME_EPOCH, 0, ZoneOffset.UTC);
    
    //User
    public static final String USER_ID = "user_id";

    public static SkyXpCharacter createCharacter() {
        SkyXpCharacter character = new SkyXpCharacter();
        character.setCharacterId(CHARACTER_ID);
        character.setCharacterName(CHARACTER_NAME);
        character.setUserId(USER_ID);
        character.addMoney(MONEY);
        return character;
    }

    public static CharacterView createCharacterView(SkyXpCharacter character) {
        CharacterView view = new CharacterView();
        view.setCharacterId(character.getCharacterId());
        view.setCharacterName(character.getCharacterName());
        return view;
    }

    public static Product createProduct() {
        return Product.builder()
            .productId(PRODUCT_ID)
            .factoryId(FACTORY_ID)
            .elementId(ELEMENT_ID)
            .amount(PRODUCT_AMOUNT)
            .addedAt(PRODUCT_ADDED_AT)
            .constructionTime(PRODUCT_CONSTRUCTION_TIME)
            .startTime(PRODUCT_START_TIME)
            .endTime(PRODUCT_END_TIME)
            .build();
    }
}
