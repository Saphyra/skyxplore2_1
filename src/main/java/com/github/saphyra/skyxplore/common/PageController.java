package com.github.saphyra.skyxplore.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PageController {
    public static final String CHARACTER_SELECT_MAPPING = "/characterselect";
    private static final String COMMUNITY_MAPPING = "/community";
    private static final String EQUIPMENT_MAPPING = "/equipment";
    private static final String FACTORY_MAPPING = "/factory";
    private static final String HANGAR_MAPPING = "/hangar";
    public static final String INDEX_MAPPING = "/";
    public static final String OVERVIEW_MAPPING = "/overview";
    private static final String SETTINGS_MAPPING = "/account";
    private static final String SHOP_MAPPING = "/shop";
    public static final String LOBBY_PAGE_MAPPING = "/lobby-page";
    public static final String LOBBY_QUEUE_MAPPING = "/lobby-queue";

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

    @GetMapping(SETTINGS_MAPPING)
    String settings() {
        log.info("Request arrived to {}", SETTINGS_MAPPING);
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
}
