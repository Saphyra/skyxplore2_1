package skyxplore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SuppressWarnings("unused")
@Controller
@RequiredArgsConstructor
@Slf4j
//TODO unit test
public class PageController {
    private static final String CHARACTER_SELECT_MAPPING = "/characterselect";
    private static final String COMMUNITY_MAPPING = "/community";
    private static final String EQUIPMENT_MAPPING = "/equipment";
    private static final String FACTORY_MAPPING = "/factory";
    private static final String HANGAR_MAPPING = "/hangar";
    private static final String INDEX_MAPPING = "/";
    private static final String OVERVIEW_MAPPING = "/overview";
    private static final String SETTINGS_MAPPING = "/account";
    private static final String SHOP_MAPPING = "/shop";

    @GetMapping(CHARACTER_SELECT_MAPPING)
    public String characterSelect(){
        log.info("Request arrived to {}", CHARACTER_SELECT_MAPPING);
        return "characterselect";
    }

    @GetMapping(COMMUNITY_MAPPING)
    public String community(){
        log.info("Request arrived to {}", COMMUNITY_MAPPING);
        return "community";
    }

    @GetMapping(EQUIPMENT_MAPPING)
    public String equipment(){
        log.info("Request arrived to {}", EQUIPMENT_MAPPING);
        return "equipment";
    }

    @GetMapping(FACTORY_MAPPING)
    public String factory(){
        log.info("Request arrived to {}", FACTORY_MAPPING);
        return "factory";
    }

    @GetMapping(HANGAR_MAPPING)
    public String hangar(){
        log.info("Request arrived to {}", HANGAR_MAPPING);
        return "hangar";
    }

    @GetMapping(INDEX_MAPPING)
    public String index(){
        log.info("Request arrived to {}", INDEX_MAPPING);
        return "index";
    }

    @GetMapping(OVERVIEW_MAPPING)
    public String overview(){
        log.info("Request arrived to {}", OVERVIEW_MAPPING);
        return "overview";
    }

    @GetMapping(SETTINGS_MAPPING)
    public String settings(){
        log.info("Request arrived to {}", SETTINGS_MAPPING);
        return "account";
    }

    @GetMapping(SHOP_MAPPING)
    public String shop(){
        log.info("Request arrived to {}", SHOP_MAPPING);
        return "shop";
    }
}
