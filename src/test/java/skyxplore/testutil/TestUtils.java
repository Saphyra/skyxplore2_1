package skyxplore.testutil;

import skyxplore.controller.request.LoginRequest;
import skyxplore.controller.request.character.*;
import skyxplore.controller.request.community.SendMailRequest;
import skyxplore.controller.request.user.*;
import skyxplore.controller.view.View;
import skyxplore.controller.view.character.CharacterView;
import skyxplore.controller.view.community.friend.FriendView;
import skyxplore.controller.view.community.friendrequest.FriendRequestView;
import skyxplore.controller.view.community.mail.MailView;
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
import skyxplore.domain.character.CharacterEntity;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.domain.community.blockedcharacter.BlockedCharacter;
import skyxplore.domain.community.blockedcharacter.BlockedCharacterEntity;
import skyxplore.domain.community.friendrequest.FriendRequest;
import skyxplore.domain.community.friendrequest.FriendRequestEntity;
import skyxplore.domain.community.friendship.Friendship;
import skyxplore.domain.community.friendship.FriendshipEntity;
import skyxplore.domain.community.mail.Mail;
import skyxplore.domain.community.mail.MailEntity;
import skyxplore.domain.credentials.Credentials;
import skyxplore.domain.credentials.CredentialsEntity;
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

    //Blocked Character
    public static final Long BLOCKED_CHARACTER_ENTITY_ID = 10L;
    public static final String BLOCKED_CHARACTER_ID = "blocked_character_id";

    //Category
    public static final String CATEGORY_ID = "category_id";
    public static final String CATEGORY_CONTENT = "category_content";

    //Character
    public static final String CHARACTER_ID = "character_id";
    public static final String CHARACTER_NAME = "character_name";
    public static final String CHARACTER_NEW_NAME = "character_new_name";
    public static final Integer CHARACTER_MONEY = 10;
    public static final String CHARACTER_ENCRYPTED_MONEY = "character_encrypted_money";
    public static final String FRIEND_NAME = "friend_name";
    public static final String CHARACTER_ENCRYPTED_EQUIPMENTS = "character_encrypted_equipments";
    public static final String CHARACTER_EQUIPMENTS = "character_equipments";

    //Converter
    public static final String CONVERTER_ENTITY = "converter_entity";
    public static final Integer CONVERTER_INT_VALUE = 316;
    public static final String CONVERTER_KEY = "converter_key";

    //Credentials
    public static final String USER_FAKE_PASSWORD = "user_fake_password";
    public static final String USER_NAME = "user_name";
    public static final String USER_NEW_PASSWORD = "user_new_password";
    public static final String USER_PASSWORD = "user_password";
    public static final String CREDENTIALS_HASHED_PASSWORD = "credentials_hashed_password";

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

    //Equip
    public static final String EQUIP_ITEM_ID = "equip_item_id";
    public static final String EQUIP_TO = "equip_to";
    public static final String UNEQUIP_FROM = "unequip_from";

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

    //Friend Request
    public static final String FRIEND_REQUEST_ID = "friend_request_id";

    //Mail
    public static final String MAIL_ID_1 = "mail_id_1";
    public static final String MAIL_FROM_ID = "mail_from_id";
    public static final String MAIL_TO_ID = "mail_to_id";
    public static final String MAIL_FROM_NAME = "mail_from_name";
    public static final String MAIL_TO_NAME = "mail_to_name";
    public static final String MAIL_SUBJECT = "mail_subject";
    public static final String MAIL_MESSAGE = "mail_message";
    public static final String MAILS_ADDRESSEE_ID = "mail_addressee_id";
    public static final Long MAIL_SEND_TIME_EPOCH = 654612L;
    public static final LocalDateTime MAIL_SEND_TIME = LocalDateTime.ofEpochSecond(MAIL_SEND_TIME_EPOCH, 0, ZoneOffset.UTC);

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
    public static final String USER_NEW_EMAIL = "user_new_email";
    public static final String USER_NEW_NAME = "user_new_name";

    public static AccessToken createAccessToken() {
        AccessToken token = new AccessToken();
        token.setAccessTokenId(ACCESS_TOKEN_ID);
        token.setUserId(USER_ID);
        token.setLastAccess(ACCESS_TOKEN_LAST_ACCESS);
        token.setCharacterId(CHARACTER_ID);
        return token;
    }

    public static AccessTokenEntity createAccessTokenEntity() {
        AccessTokenEntity entity = new AccessTokenEntity();
        entity.setAccessTokenId(ACCESS_TOKEN_ID);
        entity.setUserId(USER_ID);
        entity.setLastAccess(ACCESS_TOKEN_LAST_ACCESS_EPOCH);
        entity.setCharacterId(CHARACTER_ID);
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

    public static BlockedCharacter createBlockedCharacter() {
        return BlockedCharacter.builder()
            .blockedCharacterEntityId(BLOCKED_CHARACTER_ENTITY_ID)
            .blockedCharacterId(BLOCKED_CHARACTER_ID)
            .characterId(CHARACTER_ID)
            .build();
    }

    public static BlockedCharacterEntity createBlockedCharacterEntity() {
        BlockedCharacterEntity entity = new BlockedCharacterEntity();
        entity.setBlockedCharacterEntityId(BLOCKED_CHARACTER_ENTITY_ID);
        entity.setBlockedCharacterId(BLOCKED_CHARACTER_ID);
        entity.setCharacterId(CHARACTER_ID);
        return entity;
    }

    public static ChangeEmailRequest createChangeEmailRequest() {
        ChangeEmailRequest request = new ChangeEmailRequest();
        request.setNewEmail(USER_NEW_EMAIL);
        request.setPassword(USER_PASSWORD);
        return request;
    }

    public static ChangePasswordRequest createChangePasswordRequest() {
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
        character.addEquipment(CHARACTER_EQUIPMENTS);
        return character;
    }

    public static CharacterDeleteRequest createCharacterDeleteRequest() {
        return new CharacterDeleteRequest(CHARACTER_ID);
    }

    public static CharacterEntity createCharacterEntity() {
        CharacterEntity entity = new CharacterEntity();
        entity.setCharacterId(CHARACTER_ID);
        entity.setUserId(USER_ID);
        entity.setCharacterName(CHARACTER_NAME);
        entity.setMoney(CHARACTER_ENCRYPTED_MONEY);
        entity.setEquipments(CHARACTER_ENCRYPTED_EQUIPMENTS);
        return entity;
    }

    public static CharacterView createCharacterView(SkyXpCharacter character) {
        CharacterView view = new CharacterView();
        view.setCharacterId(character.getCharacterId());
        view.setCharacterName(character.getCharacterName());
        return view;
    }

    public static CreateCharacterRequest createCreateCharacterRequest() {
        return new CreateCharacterRequest(CHARACTER_NAME);
    }

    public static Credentials createCredentials() {
        return new Credentials(USER_ID, USER_NAME, CREDENTIALS_HASHED_PASSWORD);
    }

    public static CredentialsEntity createCredentialsEntity() {
        return new CredentialsEntity(USER_ID, USER_NAME, CREDENTIALS_HASHED_PASSWORD);
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

    public static EquipRequest createEquipRequest() {
        EquipRequest request = new EquipRequest();
        request.setItemId(EQUIP_ITEM_ID);
        request.setEquipTo(EQUIP_TO);
        return request;
    }

    public static Factory createFactory(String factoryId) {
        Factory factory = createFactory();
        factory.setFactoryId(factoryId);
        return factory;
    }

    public static Factory createFactory() {
        Factory factory = new Factory();
        factory.setFactoryId(FACTORY_ID_1);
        factory.setCharacterId(CHARACTER_ID);
        factory.setMaterials(new Materials());
        return factory;
    }

    public static FriendRequest createFriendRequest() {
        return FriendRequest.builder()
            .friendRequestId(FRIEND_REQUEST_ID)
            .friendId(FRIEND_ID)
            .characterId(CHARACTER_ID)
            .build();
    }

    public static FriendRequestEntity createFriendRequestEntity() {
        return FriendRequestEntity.builder()
            .friendRequestId(FRIEND_REQUEST_ID)
            .friendId(FRIEND_ID)
            .characterId(CHARACTER_ID)
            .build();
    }

    public static FriendRequestView createFriendRequestView() {
        FriendRequestView view = new FriendRequestView();
        view.setCharacterId(CHARACTER_ID);
        view.setFriendRequestId(FRIEND_REQUEST_ID);
        view.setFriendId(FRIEND_ID);
        view.setFriendName(FRIEND_NAME);
        return view;
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

    public static FriendView createFriendView() {
        FriendView view = new FriendView();
        view.setFriendId(FRIENDSHIP_ID);
        view.setFriendId(FRIEND_ID);
        view.setFriendName(FRIEND_NAME);
        view.setActive(false);
        return view;
    }

    public static Map<String, GeneralDescription> createGeneralDescriptionMap() {
        Map<String, GeneralDescription> map = new HashMap<>();
        map.put(DATA_ELEMENT, new TestGeneralDescription());
        return map;
    }

    public static LoginRequest createLoginRequest() {
        LoginRequest request = new LoginRequest();
        request.setUserName(USER_NAME);
        request.setPassword(USER_PASSWORD);
        return request;
    }

    public static Mail createMail() {
        return Mail.builder()
            .mailId(MAIL_ID_1)
            .from(MAIL_FROM_ID)
            .to(MAIL_TO_ID)
            .subject(MAIL_SUBJECT)
            .message(MAIL_MESSAGE)
            .read(false)
            .sendTime(MAIL_SEND_TIME)
            .archived(false)
            .deletedByAddressee(false)
            .deletedBySender(false)
            .build();
    }

    public static MailEntity createMailEntity(){
        return MailEntity.builder()
            .mailId(MAIL_ID_1)
            .from(MAIL_FROM_ID)
            .to(MAIL_TO_ID)
            .subject(MAIL_SUBJECT)
            .message(MAIL_MESSAGE)
            .read(false)
            .sendTime(MAIL_SEND_TIME_EPOCH)
            .archived(false)
            .deletedByAddressee(false)
            .deletedBySender(false)
            .build();
    }

    public static List<String> createMailIdList(String... mailIds) {
        return Arrays.asList(mailIds);
    }

    public static MailView createMailView() {
        return MailView.builder()
            .mailId(MAIL_ID_1)
            .from(MAIL_FROM_ID)
            .to(MAIL_TO_ID)
            .fromName(MAIL_FROM_NAME)
            .toName(MAIL_TO_NAME)
            .subject(MAIL_SUBJECT)
            .read(false)
            .sendTime(MAIL_SEND_TIME_EPOCH)
            .build();
    }

    public static Material createMaterial() {
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

    public static Product createProduct(String productId) {
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

    public static RenameCharacterRequest createRenameCharacterRequest() {
        return new RenameCharacterRequest(CHARACTER_NEW_NAME);
    }

    public static SendMailRequest createSendMailRequest() {
        SendMailRequest request = new SendMailRequest();
        request.setAddresseeId(MAILS_ADDRESSEE_ID);
        request.setSubject(MAIL_SUBJECT);
        request.setMessage(MAIL_MESSAGE);
        return request;
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

    public static UnequipRequest createUnequipRequest() {
        UnequipRequest request = new UnequipRequest();
        request.setItemId(EQUIP_ITEM_ID);
        request.setSlot(UNEQUIP_FROM);
        return request;
    }

    public static SkyXpUser createUser() {
        SkyXpUser user = new SkyXpUser();
        user.setUserId(USER_ID);
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
