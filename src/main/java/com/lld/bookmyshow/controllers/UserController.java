package com.lld.bookmyshow.controllers;

import com.lld.bookmyshow.dtos.*;
import com.lld.bookmyshow.models.User;
import com.lld.bookmyshow.services.UserService;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto) {
        // validate the request dto
        // call the service method
        // return the response dto
        SignUpResponseDto signUpResponseDto = new SignUpResponseDto();
        User user = userService.singUp(signUpRequestDto.getName(), signUpRequestDto.getEmail(), signUpRequestDto.getPassword());

        signUpResponseDto.setUser(user);
        signUpResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
        return signUpResponseDto;
        // in case of failure
        // signUpResponseDto.setResponseStatus(ResponseStatus.FAILURE);

    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        // validate the request dto
        // call the service method
        // return the response dto
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        try {
            User user = userService.Login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
            loginResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            loginResponseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return loginResponseDto;
    }
}
