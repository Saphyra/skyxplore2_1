package skyxplore.testutil;

import skyxplore.controller.request.character.AddToQueueRequest;
import skyxplore.controller.request.user.*;
import skyxplore.controller.view.View;
import skyxplore.controller.view.character.CharacterView;
import skyxplore.controller.view.material.MaterialView;
import skyxplore.controller.view.product.ProductView;
import skyxplore.controller.view.product.ProductViewList;
import skyxplore.controller.view.slot.SlotView;
import skyxplore.dataaccess.gamedata.entity.Material;
import skyxplore.dataaccess.gamedata.entity.Ship;
import skyxplore.dataaccess.gamedata.entity.Slot;
import skyxplore.dataaccess.gamedata.entity.abstractentity.GeneralDescription;
import skyxplore.domain.accesstoken.AccessToken;
import skyxplore.domain.accesstoken.AccessTokenEntity;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.domain.community.friendship.Friendship;
import skyxplore.domain.community.friendship.FriendshipEntity;
import skyxplore.domain.factory.Factory;
import skyxplore.domain.materials.Materials;
import skyxplore.domain.product.Product;
import skyxplore.domain.ship.EquippedShip;
import skyxplore.domain.slot.EquippedSlot;
import skyxplore.domain.user.Role;
import skyxplore.domain.user.SkyXpUser;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@SuppressWarnings({"WeakerAccess", "ArraysAsListWithZeroOrOneArgument"})
public class TestUtils {
    //ACCESS TOKEN
    public static final String ACCESS_TOKEN_ID = "access_token_id";
    public static final Long ACCESS_TOKEN_LAST_ACCESS_EPOCH = 414184L;
    public static final LocalDateTime ACCESS_TOKEN_LAST_ACCESS = LocalDateTime.ofEpochSecond(ACCESS_TOKEN_LAST_ACCESS_EPOCH, 0, ZoneOffset.UTC);

    //Category
    public static final String CATEGORY_ID = "category_id";
    public static final String CATEGORY_CONTENT = "category_content";

    //Character
    public static final String CHARACTER_ID = "character_id";
    public static final String CHARACTER_NAME = "character_name";
    public static final Integer CHARACTER_MONEY = 10;

    //CONVERTER
    public static final String CONVERTER_ENTITY = "converter_entity";
    public static final Integer CONVERTER_INT_VALUE = 316;
    public static final String CONVERTER_KEY = "converter_key";

    //Data
    public static final String DATA_ABILITY = "ability";
    public static final String DATA_CONNECTOR = "connector";
    public static final String DATA_DESCRIPTION = "data_description";
    public static final String DATA_ELEMENT = "element";
    public static final Integer DATA_ELEMENT_AMOUNT = 13;
    public static final String DATA_ITEM_FRONT = "item_front";
    public static final String DATA_ITEM_LEFT = "item_left";
    public static final String DATA_ITEM_RIGHT = "item_right";
    public static final String DATA_ITEM_BACK = "item_back";
    public static final String DATA_NAME = "data_name";
    public static final String DATA_SLOT = "data_slot";

    public static final Integer DATA_SHIP_CONNECTOR_SLOT = 5;
    public static final Integer DATA_SHIP_COREHULL = 1000;

    //EquippedShip
    public static final String EQUIPPED_SHIP_ID = "equipped_ship_id";
    public static final String EQUIPPED_SHIP_TYPE = "equipped_ship_type";

    //EquippedSlot
    public static final String DEFENSE_SLOT_ID = "defense_slot_id";
    public static final Integer EQUIPPED_SLOT_FRONT_SLOT = 2;
    public static final Integer EQUIPPED_SLOT_LEFT_SLOT = 2;
    public static final Integer EQUIPPED_SLOT_RIGHT_SLOT = 2;
    public static final Integer EQUIPPED_SLOT_BACK_SLOT = 2;
    public static final String WEAPON_SLOT_ID = "weapon_slot_id";

    //Factory
    public static final String FACTORY_ID_1 = "factory_id_1";
    public static final String FACTORY_ID_2 = "factory_id_2";
    public static final String FACTORY_ID_3 = "factory_id_3";

    //FRIENDSHIP
    public static final String FRIEND_ID = "friend_id";
    public static final String FRIENDSHIP_ID = "friendship_id";

    //Material
    public static final Integer MATERIAL_AMOUNT = 2;
    public static final Boolean MATERIAL_BUILDABLE = true;
    public static final Integer MATERIAL_BUILDPRICE = 100;
    public static final Integer MATERIAL_CONSTRUCTION_TIME = 20;
    public static final String MATERIAL_DESCRIPTION = "material_description";
    public static final String MATERIAL_ID = "material_id";
    public static final String MATERIAL_KEY = "material_id";
    public static final Integer MATERIAL_MATERIAL_AMOUNT = 3;
    public static final String MATERIAL_MATERIAL_ID = "material_material_id";
    public static final String MATERIAL_NAME = "material_name";
    public static final String MATERIAL_SLOT = "material_slot";

    //Product
    public static final Long PRODUCT_ADDED_AT = 1000L;
    public static final Integer PRODUCT_AMOUNT = 5;
    public static final Integer PRODUCT_CONSTRUCTION_TIME = 100;
    public static final String PRODUCT_ELEMENT_ID_EQUIPMENT = "element_id_equipment";
    public static final String PRODUCT_ELEMENT_ID_MATERIAL = "element_id_material";
    public static final String PRODUCT_ID_1 = "product_id_1";
    public static final String PRODUCT_ID_2 = "product_id_2";
    public static final String PRODUCT_ID_3 = "product_id_3";
    public static final Long PRODUCT_START_TIME_EPOCH = 10000L;
    public static final LocalDateTime PRODUCT_START_TIME = LocalDateTime.ofEpochSecond(PRODUCT_START_TIME_EPOCH, 0, ZoneOffset.UTC);
    public static final Long PRODUCT_END_TIME_EPOCH = 20000L;
    public static final LocalDateTime PRODUCT_END_TIME = LocalDateTime.ofEpochSecond(PRODUCT_END_TIME_EPOCH, 0, ZoneOffset.UTC);

    //Slot
    public static Integer SLOT_DEFENSE_FRONT = 2;
    public static Integer SLOT_DEFENSE_SIDE = 3;
    public static Integer SlOT_DEFENSE_BACK = 5;

    public static Integer SLOT_WEAPON_FRONT = 7;
    public static Integer SLOT_WEAPON_SIDE = 11;
    public static Integer SLOT_WEAPON_BACK = 13;

    //User
    public static final String USER_EMAIL = "user_email";
    public static final String USER_ID = "user_id";
    public static final String USER_FAKE_PASSWORD = "user_fake_password";
    public static final String USER_NAME = "user_name";
    public static final String USER_NEW_EMAIL = "user_new_email";
    public static final String USER_NEW_NAME = "user_new_name";
    public static final String USER_NEW_PASSWORD = "user_new_password";
    public static final String USER_PASSWORD = "user_password";

    public static AccessToken createAccessToken() {
        AccessToken token = new AccessToken();
        token.setAccessTokenId(ACCESS_TOKEN_ID);
        token.setUserId(USER_ID);
        token.setLastAccess(ACCESS_TOKEN_LAST_ACCESS);
        return token;
    }

    public static AccessTokenEntity createAccessTokenEntity() {
        AccessTokenEntity entity = new AccessTokenEntity();
        entity.setAccessTokenId(ACCESS_TOKEN_ID);
        entity.setUserId(USER_ID);
        entity.setLastAccess(ACCESS_TOKEN_LAST_ACCESS_EPOCH);
        return entity;
    }

    public static AccountDeleteRequest createAccountDeleteRequest() {
        AccountDeleteRequest request = new AccountDeleteRequest();
        request.setPassword(USER_PASSWORD);
        return request;
    }

    public static AddToQueueRequest createAddToQueueRequest() {
        AddToQueueRequest request = new AddToQueueRequest();
        request.setElementId(DATA_ELEMENT);
        request.setAmount(DATA_ELEMENT_AMOUNT);
        return request;
    }

    public static ChangeEmailRequest createChangeEmailRequest() {
        ChangeEmailRequest request = new ChangeEmailRequest();
        request.setNewEmail(USER_NEW_EMAIL);
        request.setPassword(USER_PASSWORD);
        return request;
    }

    public static ChangePasswordRequest createChangePasswordRequest(){
        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setNewPassword(USER_NEW_PASSWORD);
        request.setConfirmPassword(USER_NEW_PASSWORD);
        request.setOldPassword(USER_PASSWORD);
        return request;
    }

    public static ChangeUserNameRequest createChangeUserNameRequest() {
        ChangeUserNameRequest request = new ChangeUserNameRequest();
        request.setNewUserName(USER_NEW_NAME);
        request.setPassword(USER_PASSWORD);
        return request;
    }

    public static SkyXpCharacter createCharacter() {
        SkyXpCharacter character = new SkyXpCharacter();
        character.setCharacterId(CHARACTER_ID);
        character.setCharacterName(CHARACTER_NAME);
        character.setUserId(USER_ID);
        character.addMoney(CHARACTER_MONEY);
        return character;
    }

    public static CharacterView createCharacterView(SkyXpCharacter character) {
        CharacterView view = new CharacterView();
        view.setCharacterId(character.getCharacterId());
        view.setCharacterName(character.getCharacterName());
        return view;
    }

    public static Slot createDefenseSlot() {
        Slot slot = new Slot();
        slot.setFront(SLOT_DEFENSE_FRONT);
        slot.setSide(SLOT_DEFENSE_SIDE);
        slot.setBack(SlOT_DEFENSE_BACK);
        return slot;
    }

    public static EquippedSlot createEquippedDefenseSlot() {
        return createEquippedSlot(DEFENSE_SLOT_ID);
    }

    public static EquippedSlot createEquippedWeaponSlot() {
        return createEquippedSlot(WEAPON_SLOT_ID);
    }

    public static EquippedSlot createEquippedSlot(String slotId) {
        EquippedSlot slot = new EquippedSlot();
        slot.setSlotId(slotId);
        slot.setShipId(EQUIPPED_SHIP_ID);
        slot.setFrontSlot(EQUIPPED_SLOT_FRONT_SLOT);
        slot.setLeftSlot(EQUIPPED_SLOT_LEFT_SLOT);
        slot.setRightSlot(EQUIPPED_SLOT_RIGHT_SLOT);
        slot.setBackSlot(EQUIPPED_SLOT_BACK_SLOT);
        slot.addFront(DATA_ITEM_FRONT);
        slot.addLeft(DATA_ITEM_LEFT);
        slot.addRight(DATA_ITEM_RIGHT);
        slot.addBack(DATA_ITEM_BACK);
        return slot;
    }

    public static EquippedShip createEquippedShip() {
        EquippedShip ship = new EquippedShip();
        ship.setShipId(EQUIPPED_SHIP_ID);
        ship.setCharacterId(CHARACTER_ID);
        ship.setShipType(EQUIPPED_SHIP_TYPE);
        ship.setCoreHull(DATA_SHIP_COREHULL);
        ship.setConnectorSlot(DATA_SHIP_CONNECTOR_SLOT);
        ship.addConnector(DATA_CONNECTOR);
        ship.setDefenseSlotId(DEFENSE_SLOT_ID);
        ship.setWeaponSlotId(WEAPON_SLOT_ID);
        return ship;
    }

    public static Factory createFactory(String factoryId){
        Factory factory = createFactory();
        factory.setFactoryId(factoryId);
        return factory;
    }

    public static Factory createFactory(){
        Factory factory = new Factory();
        factory.setFactoryId(FACTORY_ID_1);
        factory.setCharacterId(CHARACTER_ID);
        factory.setMaterials(new Materials());
        return factory;
    }

    public static Friendship createFriendship() {
        Friendship friendship = new Friendship();
        friendship.setFriendshipId(FRIENDSHIP_ID);
        friendship.setCharacterId(CHARACTER_ID);
        friendship.setFriendId(FRIEND_ID);
        return friendship;
    }

    public static FriendshipEntity createFriendshipEntity() {
        FriendshipEntity entity = new FriendshipEntity();
        entity.setFriendshipId(FRIENDSHIP_ID);
        entity.setCharacterId(CHARACTER_ID);
        entity.setFriendId(FRIEND_ID);
        return entity;
    }

    public static Map<String, GeneralDescription> createGeneralDescriptionMap() {
        Map<String, GeneralDescription> map = new HashMap<>();
        map.put(DATA_ELEMENT, new TestGeneralDescription());
        return map;
    }

    public static Material createMaterial(){
        Material material = new Material();
        material.setBuildable(MATERIAL_BUILDABLE);
        HashMap<String, Integer> materials = new HashMap<>();
        materials.put(MATERIAL_MATERIAL_ID, MATERIAL_MATERIAL_AMOUNT);
        material.setMaterials(materials);
        material.setConstructionTime(MATERIAL_CONSTRUCTION_TIME);
        material.setBuildPrice(MATERIAL_BUILDPRICE);
        material.setId(MATERIAL_ID);
        material.setName(MATERIAL_NAME);
        material.setDescription(MATERIAL_DESCRIPTION);
        material.setSlot(MATERIAL_SLOT);
        return material;
    }

    public static MaterialView createMaterialView() {
        return MaterialView.builder()
            .materialId(MATERIAL_KEY)
            .name(MATERIAL_NAME)
            .description(MATERIAL_DESCRIPTION)
            .amount(MATERIAL_AMOUNT)
            .build();
    }

    public static Product createProduct(String productId){
        Product product = createProduct();
        product.setProductId(productId);
        return product;
    }

    public static Product createProduct() {
        return Product.builder()
            .productId(PRODUCT_ID_1)
            .factoryId(FACTORY_ID_1)
            .elementId(PRODUCT_ELEMENT_ID_EQUIPMENT)
            .amount(PRODUCT_AMOUNT)
            .addedAt(PRODUCT_ADDED_AT)
            .constructionTime(PRODUCT_CONSTRUCTION_TIME)
            .startTime(PRODUCT_START_TIME)
            .endTime(PRODUCT_END_TIME)
            .build();
    }

    public static ProductView createProductView() {
        ProductView view = new ProductView();
        view.setProductId(PRODUCT_ID_1);
        view.setFactoryId(FACTORY_ID_1);
        view.setElementId(PRODUCT_ELEMENT_ID_EQUIPMENT);
        view.setAmount(PRODUCT_AMOUNT);
        view.setAddedAt(PRODUCT_ADDED_AT);
        view.setConstructionTime(PRODUCT_CONSTRUCTION_TIME);
        view.setStartTime(PRODUCT_START_TIME_EPOCH);
        view.setEndTime(PRODUCT_END_TIME_EPOCH);
        return view;
    }

    public static ProductViewList createProductViewList() {
        return new ProductViewList(Arrays.asList(createProductView()));
    }

    public static View<ProductViewList> createProductViewListView() {
        ProductViewList productViews = createProductViewList();
        Map<String, GeneralDescription> data = createGeneralDescriptionMap();
        return new View<>(productViews, data);
    }

    public static Ship createShip() {
        Ship ship = new Ship();
        ship.setCoreHull(DATA_SHIP_COREHULL);
        ship.setConnector(DATA_SHIP_CONNECTOR_SLOT);
        ship.setDefense(createDefenseSlot());
        ship.setWeapon(createWeaponSlot());
        ArrayList<String> list = new ArrayList<>();
        list.add(DATA_ABILITY);
        ship.setAbility(list);
        return ship;
    }

    public static SlotView createSlotView(EquippedSlot slot) {
        SlotView view = new SlotView();
        view.setSlotId(slot.getSlotId());
        view.setShipId(slot.getShipId());
        return view;
    }

    public static SkyXpUser createUser() {
        SkyXpUser user = new SkyXpUser();
        user.setUserId(USER_ID);
        user.setUsername(USER_NAME);
        user.setPassword(USER_PASSWORD);
        user.setEmail(USER_EMAIL);
        HashSet<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        user.setRoles(roles);
        return user;
    }

    public static UserRegistrationRequest createUserRegistrationRequest() {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername(USER_NAME);
        request.setPassword(USER_PASSWORD);
        request.setConfirmPassword(USER_PASSWORD);
        request.setEmail(USER_EMAIL);
        return request;
    }

    public static Slot createWeaponSlot() {
        Slot slot = new Slot();
        slot.setFront(SLOT_WEAPON_FRONT);
        slot.setSide(SLOT_WEAPON_SIDE);
        slot.setBack(SLOT_WEAPON_BACK);
        return slot;
    }
}
