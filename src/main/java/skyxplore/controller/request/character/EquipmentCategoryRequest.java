package skyxplore.controller.request.character;

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
    WEAPON("weapon"),
    MATERIAL("material");

    private String value;

    EquipmentCategoryRequest(String value) {
        this.value = value;
    }

    public static EquipmentCategoryRequest fromValue(String value) {
        for (EquipmentCategoryRequest element : values()) {
            if (element.value.equalsIgnoreCase(value)) {
                return element;
            }
        }
        return null;
    }
}
