package com.woodpecker.controller.login;

import com.woodpecker.model.user.User;
import com.woodpecker.controller.AuthUser;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.woodpecker.controller.login.LoginController.REST_URL;

@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@CrossOrigin("*")
public class LoginController {

    public final static String REST_URL = "/rest/login";

    @GetMapping
    public User get(@AuthenticationPrincipal AuthUser authUser) {
        return authUser.getUser();
    }

}