package com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.fill;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.fill.fromlobby.FillFromLobbyService;
import com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.fill.withai.FillWithAiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FillGroupingService {
    private final FillFromLobbyService fillFromLobbyService;
    private final FillWithAiService fillWithAiService;

    public void fillGroupingsWithLobbies(){
        fillFromLobbyService.fillGroupingsWithLobbies();
    }

    public void fillGroupingsWithAis() {
        fillWithAiService.fillWithAi();
    }
}
