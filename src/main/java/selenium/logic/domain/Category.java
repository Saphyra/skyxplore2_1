package selenium.logic.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Category {
    ENERGY("energy"), EXTENDER("extender"), STARTER_SHIP("starter");

    @Getter
    private final String categoryId;
}
