package skyxplore.pagecontroller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PageController {
    private static final String CHARACTER_SELECT_MAPPING = "/characterselect";
    private static final String INDEX_MAPPING = "/";
    private static final String OVERVIEW_MAPPING = "/overview";

    @GetMapping(CHARACTER_SELECT_MAPPING)
    public String characterSelect(){
        log.info("Request arrived to {}", CHARACTER_SELECT_MAPPING);
        return "characterselect";
    }

    @GetMapping(INDEX_MAPPING)
    public String index(){
        log.info("Request arrived to {}", INDEX_MAPPING);
        return "index";
    }

    @GetMapping(OVERVIEW_MAPPING)
    public String overview(){
        return "overview";
    }
}
