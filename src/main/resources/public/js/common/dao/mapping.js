window.Mapping = {
    CHANGE_EMAIL: "user/email",
    CHANGE_PASSWORD: "user/password",
    CHANGE_USERNAME: "user/name",
    CHARACTER_NAME_EXISTS: "character/name",
    CHARACTER_SELECT: "characterselect",
    CREATE_CHARACTER: "character",
    DELETE_ACCOUNT: "user",
    EMAIL_EXISTS: "user/email",
    GET_CHARACTERS: "character",
    LOGIN: "login",
    LOGOUT: "logout",
    REGISTER: "user",
    SELECT_CHARACTER: "character/*",
    USERNAME_EXISTS: "user/name",
    
    concat: function(path, id){
        return path.replace("*", id);
    }
}