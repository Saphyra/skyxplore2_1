package skyxplore.home.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {
    private static final String HOME_MAPPING = "/";

    @GetMapping(HOME_MAPPING)
    public String home(){
        log.info("Request arrived to {}", HOME_MAPPING);
        return "index";
    }
}
