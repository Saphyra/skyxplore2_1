package skyxplore.restcontroller.request;

public enum EquipmentCategoryRequest {
    ALL("all"),
    CONNECTOR("connector"),
    ENERGY("energy"),
    EXTENDER("extender"),
    COREHULL("corehull"),
    STORAGE("storage"),
    DEFENSE("defense"),
    ARMOR("armor"),
    SHIELD("shield"),
    SHIP("ship"),
    WEAPON("weapon");

    private String value;

    EquipmentCategoryRequest(String value){
        this.value = value;
    }

    public static EquipmentCategoryRequest fromValue(String value){
        for(EquipmentCategoryRequest element : values()){
            if(element.value.equals(value)){
                return element;
            }
        }
        return null;
    }
}
