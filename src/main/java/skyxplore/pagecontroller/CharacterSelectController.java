package skyxplore.pagecontroller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class CharacterSelectController {

    private static final String CHARACTER_SELECT_MAPPING = "/characterselect";

    @GetMapping(CHARACTER_SELECT_MAPPING)
    public String home(){
        log.info("Request arrived to {}", CHARACTER_SELECT_MAPPING);
        return "characterselect";
    }
}
