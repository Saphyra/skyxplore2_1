package com.github.saphyra.skyxplore.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.github.saphyra.skyxplore.common.RequestConstants.WEB_PREFIX;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PageController {
    public static final String ACCOUNT_MAPPING = WEB_PREFIX + "/account";
    public static final String CHARACTER_SELECT_MAPPING = WEB_PREFIX + "/characterselect";
    public static final String COMMUNITY_MAPPING = WEB_PREFIX + "/community";
    public static final String EQUIPMENT_MAPPING = WEB_PREFIX + "/equipment";
    public static final String FACTORY_MAPPING = WEB_PREFIX + "/factory";
    public static final String HANGAR_MAPPING = WEB_PREFIX + "/hangar";
    public static final String INDEX_MAPPING = WEB_PREFIX + "/";
    public static final String OVERVIEW_MAPPING = WEB_PREFIX + "/overview";
    public static final String SHOP_MAPPING = WEB_PREFIX + "/shop";
    public static final String LOBBY_PAGE_MAPPING = WEB_PREFIX + "/lobby-page";
    public static final String LOBBY_QUEUE_MAPPING = WEB_PREFIX + "/lobby-queue";

    @GetMapping(CHARACTER_SELECT_MAPPING)
    String characterSelect() {
        log.info("Request arrived to {}", CHARACTER_SELECT_MAPPING);
        return "characterselect";
    }

    @GetMapping(COMMUNITY_MAPPING)
    String community() {
        log.info("Request arrived to {}", COMMUNITY_MAPPING);
        return "community";
    }

    @GetMapping(EQUIPMENT_MAPPING)
    String equipment() {
        log.info("Request arrived to {}", EQUIPMENT_MAPPING);
        return "equipment";
    }

    @GetMapping(FACTORY_MAPPING)
    String factory() {
        log.info("Request arrived to {}", FACTORY_MAPPING);
        return "factory";
    }

    @GetMapping(HANGAR_MAPPING)
    String hangar() {
        log.info("Request arrived to {}", HANGAR_MAPPING);
        return "hangar";
    }

    @GetMapping(INDEX_MAPPING)
    String index() {
        log.info("Request arrived to {}", INDEX_MAPPING);
        return "index";
    }

    @GetMapping(OVERVIEW_MAPPING)
    String overview() {
        log.info("Request arrived to {}", OVERVIEW_MAPPING);
        return "overview";
    }

    @GetMapping(ACCOUNT_MAPPING)
    String account() {
        log.info("Request arrived to {}", ACCOUNT_MAPPING);
        return "account";
    }

    @GetMapping(SHOP_MAPPING)
    String shop() {
        log.info("Request arrived to {}", SHOP_MAPPING);
        return "shop";
    }

    @GetMapping(LOBBY_PAGE_MAPPING)
    String lobby() {
        log.info("Request arrived to {}", LOBBY_PAGE_MAPPING);
        return "lobby";
    }

    @GetMapping(LOBBY_QUEUE_MAPPING)
    String lobbyQueue() {
        log.info("Request arrived to {}", LOBBY_QUEUE_MAPPING);
        return "lobby_queue";
    }
}
