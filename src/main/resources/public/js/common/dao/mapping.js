window.Mapping = {
    BUY_ITEMS: "character/equipment",
    CHANGE_EMAIL: "user/email",
    CHANGE_PASSWORD: "user/password",
    CHANGE_USERNAME: "user/name",
    CHARACTER_MONEY: "character/money",
    CHARACTER_NAME_EXISTS: "character/name",
    CHARACTER_SELECT: "characterselect",
    CREATE_CHARACTER: "character",
    DELETE_ACCOUNT: "user",
    DELETE_CHARACTER: "character/*",
    EMAIL_EXISTS: "user/email",
    EQUIP_ITEM: "ship/equipment",
    EQUIP_SHIP: "ship/equipment/*",
    EQUIPMENT_STORAGE: "character/storage",
    GET_CHARACTERS: "character",
    GET_SHIP_DATA: "ship",
    ITEMS_OF_CATEGORY: "categories/*",
    LOGIN: "login",
    LOGOUT: "logout",
    NUMBER_OF_NOTIFICATIONS: "notification",
    REGISTER: "user",
    RENAME_CHARACTER: "character",
    SELECT_CHARACTER: "character/*",
    UNEQUIP_ITEM: "ship/equipment",
    USERNAME_EXISTS: "user/name",
    
    concat: function(path, id){
        return path.replace("*", id);
    }
}