package skyxplore.pages.home.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HomeController {

    private static final String HOME_MAPPING = "/home";

    @GetMapping(HOME_MAPPING)
    public String home(){
        log.info("Request arrived to {}", HOME_MAPPING);
        return "home";
    }
}
