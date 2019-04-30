package skyxplore.testutil;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.character.domain.request.CreateCharacterRequest;
import org.github.saphyra.skyxplore.character.domain.request.RenameCharacterRequest;
import org.github.saphyra.skyxplore.character.domain.view.character.CharacterView;
import org.github.saphyra.skyxplore.community.blockedcharacter.domain.BlockedCharacter;
import org.github.saphyra.skyxplore.community.friendship.domain.FriendRequest;
import org.github.saphyra.skyxplore.community.friendship.domain.Friendship;
import org.github.saphyra.skyxplore.user.domain.AccountDeleteRequest;
import org.github.saphyra.skyxplore.user.domain.ChangeEmailRequest;
import org.github.saphyra.skyxplore.user.domain.ChangePasswordRequest;
import org.github.saphyra.skyxplore.user.domain.ChangeUserNameRequest;
import org.github.saphyra.skyxplore.user.domain.Role;
import org.github.saphyra.skyxplore.user.domain.SkyXpCredentials;
import org.github.saphyra.skyxplore.user.domain.SkyXpUser;
import org.github.saphyra.skyxplore.user.domain.UserRegistrationRequest;

import skyxplore.controller.request.character.AddToQueueRequest;
import skyxplore.controller.request.character.EquipRequest;
import skyxplore.controller.request.character.UnequipRequest;
import skyxplore.controller.request.community.SendMailRequest;
import skyxplore.controller.view.community.friend.FriendView;
import skyxplore.controller.view.community.friendrequest.FriendRequestView;
import skyxplore.controller.view.community.mail.MailView;
import skyxplore.controller.view.product.ProductView;
import skyxplore.controller.view.ship.ShipView;
import skyxplore.controller.view.slot.SlotView;
import skyxplore.dataaccess.gamedata.entity.Material;
import skyxplore.dataaccess.gamedata.entity.Ship;
import skyxplore.dataaccess.gamedata.entity.Slot;
import skyxplore.domain.community.mail.Mail;
import skyxplore.domain.community.mail.MailEntity;
import skyxplore.domain.factory.Factory;
import skyxplore.domain.factory.FactoryEntity;
import skyxplore.domain.materials.Materials;
import skyxplore.domain.product.Product;
import skyxplore.domain.product.ProductEntity;
import skyxplore.domain.ship.EquippedShip;
import skyxplore.domain.ship.EquippedShipEntity;
import skyxplore.domain.slot.EquippedSlot;
import skyxplore.domain.slot.SlotEntity;

@Deprecated
public class TestUtils {
    //Blocked Character
    private static final Long BLOCKED_CHARACTER_ENTITY_ID = 10L;
    public static final String BLOCKED_CHARACTER_ID = "blocked_character_id";

    //Category
    public static final String CATEGORY_ID = "category_id";

    //Character
    public static final String CHARACTER_ID_1 = "character_id_1";
    public static final String CHARACTER_ID_2 = "character_id_2";
    public static final String CHARACTER_NAME = "character_name";
    public static final String CHARACTER_NEW_NAME = "character_new_name";
    public static final Integer CHARACTER_MONEY = 10;
    public static final String FRIEND_NAME = "friend_name";
    private static final String CHARACTER_EQUIPMENT = "character_equipments";

    //Converter
    public static final String CONVERTER_ENTITY = "converter_entity";
    public static final Integer CONVERTER_INT_VALUE = 316;
    public static final String CONVERTER_KEY = "converter_key";

    //SkyXpCredentials
    public static final String USER_FAKE_PASSWORD = "user_fake_password";
    public static final String USER_NAME = "user_name";
    public static final String USER_NEW_PASSWORD = "user_new_password";
    public static final String USER_PASSWORD = "user_password";
    public static final String CREDENTIALS_HASHED_PASSWORD = "credentials_hashed_password";

    //Data
    public static final String DATA_ID_1 = "data_id_1";
    public static final String DATA_ID_2 = "data_id_2";
    public static final String DATA_ABILITY = "ability";
    public static final String DATA_CONNECTOR = "connector";
    public static final String DATA_ELEMENT = "element";
    public static final Integer DATA_ELEMENT_AMOUNT = 13;
    public static final String DATA_ITEM_FRONT = "item_front";
    public static final String DATA_ITEM_LEFT = "item_left";
    public static final String DATA_ITEM_RIGHT = "item_right";
    public static final String DATA_ITEM_BACK = "item_back";
    public static final String DATA_NAME = "data_name";
    public static final String DATA_SLOT = "data_slot";
    public static final String DATA_CATEGORY_1 = "data_category_1";
    public static final String DATA_CATEGORY_2 = "data_category_2";

    public static final Integer DATA_SHIP_CONNECTOR_SLOT = 5;
    public static final Integer DATA_SHIP_COREHULL = 1000;

    //Equip
    public static final String EQUIP_ITEM_ID = "equip_item_id";
    private static final String EQUIP_TO = "equip_to";
    private static final String UNEQUIP_FROM = "unequip_from";

    //EquippedShip
    public static final String EQUIPPED_SHIP_ID = "equipped_ship_id";
    public static final String EQUIPPED_SHIP_TYPE = "equipped_ship_type";
    public static final String EQUIPPED_SHIP_CONNECTOR_EQUIPPED = "equipped_ship_connector_equipped";
    public static final String EQUIPPED_SHIP_ENCRYPTED_SHIP_TYPE = "equipped_ship_encrypted_type";
    public static final String EQUIPPED_SHIP_ENCRYPTED_COREHULL = "equipped_ship_encrypted_corehull";
    public static final String EQUIPPED_SHIP_ENCRYPTED_CONNECTOR_SLOT = "equipped_ship_encrypted_connector_slot";
    public static final String EQUIPPED_SHIP_ENCRYPTED_CONNECTOR_EQUIPPED = "equipped_ship_encrypted_connector_equipped";

    //EquippedSlot
    public static final String EQUIPPED_SLOT_ID = "equipped_slot_id";
    public static final String DEFENSE_SLOT_ID = "defense_slot_id";
    public static final Integer EQUIPPED_SLOT_FRONT_SLOT = 2;
    public static final Integer EQUIPPED_SLOT_LEFT_SLOT = 2;
    public static final Integer EQUIPPED_SLOT_RIGHT_SLOT = 2;
    public static final Integer EQUIPPED_SLOT_BACK_SLOT = 2;
    public static final String EQUIPPED_SLOT_ENCRYPTED_SLOT = "equipped_slot_encrypted_slot";
    public static final String EQUIPPED_SLOT_ENCRYPTED_SLOT_ITEM = "equipped_slot_encrypted_slot_item";
    public static final String EQUIPPED_SLOT_DATA_ITEM_STRING = "equipped_slot_data_item_string";
    public static final String WEAPON_SLOT_ID = "weapon_slot_id";
    private static final String EQUIPPED_SLOT_ENCRYPTED_FRONT_SLOT = "equipped_slot_encrypted_front_slot";
    private static final String EQUIPPED_SLOT_ENCRYPTED_LEFT_SLOT = "equipped_slot_encrypted_left_slot";
    private static final String EQUIPPED_SLOT_ENCRYPTED_RIGHT_SLOT = "equipped_slot_encrypted_right_slot";
    private static final String EQUIPPED_SLOT_ENCRYPTED_BACK_SLOT = "equipped_slot_encrypted_back_slot";
    private static final String EQUIPPED_SLOT_ENCRYPTED_FRONT_ITEM = "equipped_slot_encrypted_front_item";
    private static final String EQUIPPED_SLOT_ENCRYPTED_LEFT_ITEM = "equipped_slot_encrypted_left_item";
    private static final String EQUIPPED_SLOT_ENCRYPTED_RIGHT_ITEM = "equipped_slot_encrypted_right_item";
    private static final String EQUIPPED_SLOT_ENCRYPTED_BACK_ITEM = "equipped_slot_encrypted_back_item";

    //Factory
    public static final String FACTORY_ID_1 = "factory_id_1";
    public static final String FACTORY_ID_2 = "factory_id_2";
    public static final String FACTORY_ID_3 = "factory_id_3";
    public static final String FACTORY_MATERIALS = "factory_materials";

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
    public static final String MAIL_ENCRYPTED_SUBJECT = "mail_encrypted_subject";
    public static final String MAIL_MESSAGE = "mail_message";
    public static final String MAIL_ENCRYPTED_MESSAGE = "mail_encrypted_message";
    public static final String MAILS_ADDRESSEE_ID = "mail_addressee_id";
    public static final Long MAIL_SEND_TIME_EPOCH = 654612L;
    public static final OffsetDateTime MAIL_SEND_TIME = OffsetDateTime.of(LocalDateTime.ofEpochSecond(MAIL_SEND_TIME_EPOCH, 0, ZoneOffset.UTC), ZoneOffset.UTC);

    //Material
    public static final String MATERIAL_ENCRYPTED_ENTITY = "material_encrypted_entity";
    public static final String MATERIAL_ENTITY = "material_entity";
    public static final Integer MATERIAL_AMOUNT = 2;
    private static final Boolean MATERIAL_BUILDABLE = true;
    private static final Integer MATERIAL_BUILDPRICE = 100;
    private static final Integer MATERIAL_CONSTRUCTION_TIME = 20;
    public static final String MATERIAL_ID = "material_id";
    public static final String MATERIAL_KEY = "material_KEY";
    public static final Integer MATERIAL_MATERIAL_AMOUNT = 3;
    private static final String MATERIAL_MATERIAL_ID = "material_material_id";
    private static final String MATERIAL_SLOT = "material_slot";

    //Product
    public static final Long PRODUCT_ADDED_AT = 1000L;
    public static final Integer PRODUCT_AMOUNT = 5;
    public static final String PRODUCT_ENCRYPTED_AMOUNT = "product_encrypted_amount";
    public static final Integer PRODUCT_CONSTRUCTION_TIME = 100;
    public static final Integer PRODUCT_BUILD_PRICE = 50;
    public static final String PRODUCT_ENCRYPTED_CONSTRUCTION_TIME = "product_encrypted_construction_time";
    public static final String PRODUCT_ELEMENT_ID_EQUIPMENT = "element_id_equipment";
    public static final String PRODUCT_ELEMENT_ID_MATERIAL = "element_id_material";
    public static final String PRODUCT_ENCRYPTED_ELEMENT_ID = "product_encrypted_element_id";
    public static final String PRODUCT_ID_1 = "product_id_1";
    public static final String PRODUCT_ID_2 = "product_id_2";
    public static final String PRODUCT_ID_3 = "product_id_3";
    public static final Long PRODUCT_START_TIME_EPOCH = 10000L;
    public static final OffsetDateTime PRODUCT_START_TIME = OffsetDateTime.of(LocalDateTime.ofEpochSecond(PRODUCT_START_TIME_EPOCH, 0, ZoneOffset.UTC), ZoneOffset.UTC);
    public static final Long PRODUCT_END_TIME_EPOCH = 20000L;
    public static final OffsetDateTime PRODUCT_END_TIME = OffsetDateTime.of(LocalDateTime.ofEpochSecond(PRODUCT_END_TIME_EPOCH, 0, ZoneOffset.UTC), ZoneOffset.UTC);

    //User
    public static final String USER_EMAIL = "user_email";
    public static final String USER_ID = "user_id";
    public static final String USER_NEW_EMAIL = "user_new_email";
    public static final String USER_NEW_NAME = "user_new_name";

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
            .characterId(CHARACTER_ID_1)
            .build();
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
        character.setCharacterId(CHARACTER_ID_1);
        character.setCharacterName(CHARACTER_NAME);
        character.setUserId(USER_ID);
        character.addMoney(CHARACTER_MONEY);
        character.addEquipment(CHARACTER_EQUIPMENT);
        return character;
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

    public static SkyXpCredentials createCredentials() {
        return new SkyXpCredentials(USER_ID, USER_NAME, CREDENTIALS_HASHED_PASSWORD);
    }


    private static Slot createDefenseSlot() {
        Slot slot = new Slot();
        //Slot
        Integer SLOT_DEFENSE_FRONT = 2;
        slot.setFront(SLOT_DEFENSE_FRONT);
        Integer SLOT_DEFENSE_SIDE = 3;
        slot.setSide(SLOT_DEFENSE_SIDE);
        Integer slOT_DEFENSE_BACK = 5;
        slot.setBack(slOT_DEFENSE_BACK);
        return slot;
    }

    public static EquippedSlot createEquippedDefenseSlot() {
        return createEquippedSlot(DEFENSE_SLOT_ID);
    }

    public static EquippedSlot createEquippedWeaponSlot() {
        return createEquippedSlot(WEAPON_SLOT_ID);
    }

    public static EquippedShip createEquippedShip() {
        EquippedShip ship = new EquippedShip();
        ship.setShipId(EQUIPPED_SHIP_ID);
        ship.setCharacterId(CHARACTER_ID_1);
        ship.setShipType(EQUIPPED_SHIP_TYPE);
        ship.setCoreHull(DATA_SHIP_COREHULL);
        ship.setConnectorSlot(DATA_SHIP_CONNECTOR_SLOT);
        ship.addConnector(DATA_CONNECTOR);
        ship.setDefenseSlotId(DEFENSE_SLOT_ID);
        ship.setWeaponSlotId(WEAPON_SLOT_ID);
        return ship;
    }

    public static EquippedShipEntity createEquippedShipEntity() {
        EquippedShipEntity entity = new EquippedShipEntity();
        entity.setShipId(EQUIPPED_SHIP_ID);
        entity.setCharacterId(CHARACTER_ID_1);
        entity.setShipType(EQUIPPED_SHIP_ENCRYPTED_SHIP_TYPE);
        entity.setCoreHull(EQUIPPED_SHIP_ENCRYPTED_COREHULL);
        entity.setConnectorSlot(EQUIPPED_SHIP_ENCRYPTED_CONNECTOR_SLOT);
        entity.setConnectorEquipped(EQUIPPED_SHIP_ENCRYPTED_CONNECTOR_EQUIPPED);
        entity.setDefenseSlotId(DEFENSE_SLOT_ID);
        entity.setWeaponSlotId(WEAPON_SLOT_ID);
        return entity;
    }

    public static ShipView createShipView() {
        return ShipView.builder()
            .shipType(EQUIPPED_SHIP_TYPE)
            .coreHull(DATA_SHIP_COREHULL)
            .connectorSlot(DATA_SHIP_CONNECTOR_SLOT)
            .connectorEquipped(Arrays.asList(DATA_CONNECTOR))
            .defenseSlot(createSlotView(createEquippedDefenseSlot()))
            .defenseSlot(createSlotView(createEquippedWeaponSlot()))
            .ability(Arrays.asList(DATA_ABILITY))
            .build();
    }

    public static EquippedSlot createEquippedSlot(String slotId) {
        EquippedSlot slot = createEquippedSlot();
        slot.setSlotId(slotId);
        return slot;
    }

    public static EquippedSlot createEquippedSlot() {
        EquippedSlot slot = new EquippedSlot();
        slot.setSlotId(EQUIPPED_SLOT_ID);
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
        factory.setCharacterId(CHARACTER_ID_1);
        factory.setMaterials(createMaterials());
        return factory;
    }

    public static FactoryEntity createFactoryEntity() {
        FactoryEntity entity = new FactoryEntity();
        entity.setFactoryId(FACTORY_ID_1);
        entity.setCharacterId(CHARACTER_ID_1);
        entity.setMaterials(FACTORY_MATERIALS);
        return entity;
    }

    public static FriendRequest createFriendRequest() {
        return FriendRequest.builder()
            .friendRequestId(FRIEND_REQUEST_ID)
            .friendId(FRIEND_ID)
            .characterId(CHARACTER_ID_1)
            .build();
    }

    public static FriendRequestView createFriendRequestView() {
        FriendRequestView view = new FriendRequestView();
        view.setCharacterId(CHARACTER_ID_1);
        view.setFriendRequestId(FRIEND_REQUEST_ID);
        view.setFriendId(FRIEND_ID);
        view.setFriendName(FRIEND_NAME);
        return view;
    }

    public static Friendship createFriendship() {
        Friendship friendship = new Friendship();
        friendship.setFriendshipId(FRIENDSHIP_ID);
        friendship.setCharacterId(CHARACTER_ID_1);
        friendship.setFriendId(FRIEND_ID);
        return friendship;
    }

    public static FriendView createFriendView() {
        FriendView view = new FriendView();
        view.setFriendId(FRIENDSHIP_ID);
        view.setFriendId(FRIEND_ID);
        view.setFriendName(FRIEND_NAME);
        view.setActive(false);
        return view;
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

    public static MailEntity createMailEntity() {
        return MailEntity.builder()
            .mailId(MAIL_ID_1)
            .from(MAIL_FROM_ID)
            .to(MAIL_TO_ID)
            .subject(MAIL_ENCRYPTED_SUBJECT)
            .message(MAIL_ENCRYPTED_MESSAGE)
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
        material.setSlot(MATERIAL_SLOT);
        return material;
    }

    public static Materials createMaterials() {
        Materials materials = new Materials();
        materials.addMaterial(MATERIAL_KEY, MATERIAL_AMOUNT);
        return materials;
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

    public static ProductEntity createProductEntity() {
        return ProductEntity.builder()
            .productId(PRODUCT_ID_1)
            .factoryId(FACTORY_ID_1)
            .elementId(PRODUCT_ENCRYPTED_ELEMENT_ID)
            .amount(PRODUCT_ENCRYPTED_AMOUNT)
            .addedAt(PRODUCT_ADDED_AT)
            .constructionTime(PRODUCT_ENCRYPTED_CONSTRUCTION_TIME)
            .startTime(PRODUCT_START_TIME_EPOCH)
            .endTime(PRODUCT_END_TIME_EPOCH)
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

    public static RenameCharacterRequest createRenameCharacterRequest() {
        return new RenameCharacterRequest(CHARACTER_NEW_NAME, CHARACTER_ID_1);
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

    public static SlotEntity createSlotEntity() {
        SlotEntity entity = new SlotEntity();
        entity.setSlotId(EQUIPPED_SLOT_ID);
        entity.setShipId(EQUIPPED_SHIP_ID);
        entity.setFrontSlot(EQUIPPED_SLOT_ENCRYPTED_FRONT_SLOT);
        entity.setLeftSlot(EQUIPPED_SLOT_ENCRYPTED_LEFT_SLOT);
        entity.setRightSlot(EQUIPPED_SLOT_ENCRYPTED_RIGHT_SLOT);
        entity.setBackSlot(EQUIPPED_SLOT_ENCRYPTED_BACK_SLOT);
        entity.setFrontEquipped(EQUIPPED_SLOT_ENCRYPTED_FRONT_ITEM);
        entity.setLeftEquipped(EQUIPPED_SLOT_ENCRYPTED_LEFT_ITEM);
        entity.setRightEquipped(EQUIPPED_SLOT_ENCRYPTED_RIGHT_ITEM);
        entity.setBackEquipped(EQUIPPED_SLOT_ENCRYPTED_BACK_ITEM);
        return entity;
    }

    public static SlotView createSlotView(EquippedSlot slot) {
        return SlotView.builder()
            .frontSlot(slot.getFrontSlot())
            .frontEquipped(slot.getFrontEquipped())
            .rightSlot(slot.getRightSlot())
            .rightEquipped(slot.getRightEquipped())
            .backSlot(slot.getBackSlot())
            .backEquipped(slot.getBackEquipped())
            .leftSlot(slot.getLeftSlot())
            .leftEquipped(slot.getLeftEquipped())
            .build();
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
        request.setEmail(USER_EMAIL);
        return request;
    }

    private static Slot createWeaponSlot() {
        Slot slot = new Slot();
        Integer SLOT_WEAPON_FRONT = 7;
        slot.setFront(SLOT_WEAPON_FRONT);
        Integer SLOT_WEAPON_SIDE = 11;
        slot.setSide(SLOT_WEAPON_SIDE);
        Integer SLOT_WEAPON_BACK = 13;
        slot.setBack(SLOT_WEAPON_BACK);
        return slot;
    }
}
