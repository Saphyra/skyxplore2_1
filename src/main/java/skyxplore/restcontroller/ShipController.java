package skyxplore.restcontroller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import skyxplore.filter.AuthFilter;

@RestController
@Slf4j
public class ShipController {
    private static final String GET_SHIP_DATA_MAPPING = "ship/getshipdata/{shipId}";

    @GetMapping(GET_SHIP_DATA_MAPPING)
    public void setGetShipData(@PathVariable(value = "shipId") Integer shipId, @CookieValue(AuthFilter.COOKIE_USER_ID) Long userId){
        log.info("Querying ship data of ship {}", shipId);
    }
}
