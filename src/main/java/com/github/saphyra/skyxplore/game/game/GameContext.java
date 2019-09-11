package com.github.saphyra.skyxplore.game.game;

import com.github.saphyra.skyxplore.game.game.service.message.GameMessageFactory;
import com.github.saphyra.util.IdGenerator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
public class GameContext {
    private final GameMessageFactory gameMessageFactory;
    private final IdGenerator idGenerator;
}
