package selenium.logic.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ContainerId {
    FRONT_WEAPON("front-weapon"),
    RIGHT_WEAPON("right-weapon"),
    BACK_WEAPON("back-weapon"),
    LEFT_WEAPON("left-weapon"),

    FRONT_DEFENSE("front-defense"),
    RIGHT_DEFENSE("right-defense"),
    BACK_DEFENSE("back-defense"),
    LEFT_DEFENSE("left-defense"),

    CONNECTORS("connectors");

    @Getter
    private final String id;
}