package skyxplore.pagecontroller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class IndexController {
    private static final String INDEX_MAPPING = "/";

    @GetMapping(INDEX_MAPPING)
    public String index(){
        log.info("Request arrived to {}", INDEX_MAPPING);
        return "index";
    }
}
