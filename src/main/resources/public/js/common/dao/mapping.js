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
    GET_CHARACTERS: "character",
    ITEMS_OF_CATEGORY: "categories/*",
    LOGIN: "login",
    LOGOUT: "logout",
    NUMBER_OF_NOTIFICATIONS: "notification",
    REGISTER: "user",
    RENAME_CHARACTER: "character",
    SELECT_CHARACTER: "character/*",
    SHIP_DATA: "ship",
    USERNAME_EXISTS: "user/name",
    
    concat: function(path, id){
        return path.replace("*", id);
    }
}