package selenium.cases.equipment.testcase.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ContainerId {
    FRONT_WEAPON("frontweapon"),
    RIGHT_WEAPON("rightweapon"),
    BACK_WEAPON("backweapon"),
    LEFT_WEAPON("leftweapon"),

    FRONT_DEFENSE("frontdefense"),
    RIGHT_DEFENSE("rightdefense"),
    BACK_DEFENSE("backdefense"),
    LEFT_DEFENSE("leftdefense"),

    CONNECTORS("connectors");

    @Getter
    private final String id;
}