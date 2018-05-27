package skyxplore.home.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import skyxplore.home.controller.request.UserRegistrationRequest;
import skyxplore.home.service.UserService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HomeRestController {
    private final UserService userService;

    @PutMapping("/registration")
    public void registration(@Valid UserRegistrationRequest request){
        log.info("Method called.");
        userService.registrateUser(request);
    }

    @GetMapping("api/isusernameexists")
    public boolean isUsernameExists(@RequestParam(value = "username", required = true) String userName){
        return userService.isUserNameExists(userName);
    }
}
