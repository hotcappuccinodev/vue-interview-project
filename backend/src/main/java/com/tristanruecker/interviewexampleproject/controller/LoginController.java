package com.tristanruecker.interviewexampleproject.controller;

import com.tristanruecker.interviewexampleproject.models.objects.Captcha;
import com.tristanruecker.interviewexampleproject.models.objects.UserEmailAndPassword;
import com.tristanruecker.interviewexampleproject.models.objects.User;
import com.tristanruecker.interviewexampleproject.models.response.UserLoggedInResponse;
import com.tristanruecker.interviewexampleproject.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping(value = "/login")
    public UserLoggedInResponse login(@RequestBody UserEmailAndPassword user) {
        return loginService.userLogin(user);
    }


    @PostMapping(value = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody User user) {
        loginService.registerUser(user);
    }

    @GetMapping(value = "/register/captcha")
    public Captcha registerUser() {
        return loginService.getCaptcha();
    }

    /**
     * https://stackoverflow.com/questions/35791465/is-there-a-way-to-parse-claims-from-an-expired-jwt-token
     * TODO: Get new authentication token BUT NOT HERE old ist "just" expired
     */
    @PostMapping(value = "/renewToken")
    public UserLoggedInResponse regenerateTokenOnExpire(@RequestHeader("Authorization") String authorizationHeader, Principal principal) {
        return loginService.regenerateTokenOnExpire(authorizationHeader, principal);
    }

}
