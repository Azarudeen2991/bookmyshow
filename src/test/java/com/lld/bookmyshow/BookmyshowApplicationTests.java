package com.lld.bookmyshow;

import com.lld.bookmyshow.controllers.UserController;
import com.lld.bookmyshow.dtos.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookmyshowApplicationTests {

	@Autowired
	UserController userController;

	@Test
	void contextLoads() {
	}

	@Test
	public void testSignUp() {
		SignUpRequestDto signUpRequestDto = new SignUpRequestDto();
		signUpRequestDto.setName("Azarudeen");
		signUpRequestDto.setEmail("Azarudeen.H@xyz.com");
		signUpRequestDto.setPassword("azarudeen123");

		SignUpResponseDto signUpResponseDto = userController.signUp(signUpRequestDto);

		System.out.println(signUpResponseDto.getUser().getId());
	}

	@Test
	public void testLogin() {
		// TODO
		LoginRequestDto loginRequestDto = new LoginRequestDto();
		loginRequestDto.setEmail("John.Doe@xyz.com");
		loginRequestDto.setPassword("abcd1234");

		LoginResponseDto responseDto = userController.login(loginRequestDto);

		if(responseDto.getResponseStatus().equals(ResponseStatus.SUCCESS)) {
			System.out.println("Login Successful");
		} else {
			System.out.println("Login Failed");
		}
	}
}
