package com.lld.bookmyshow.services;

import com.lld.bookmyshow.exceptions.UserNotFoundException;
import com.lld.bookmyshow.models.User;
import com.lld.bookmyshow.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User singUp(String name, String email, String password) {



        /*
        1. check if user with email already exists
        2. if exists, ask user to login
        3. if not exists, create new user object
        4. save the user object to the database
        5. return the user object
         */

        User user = new User();
        user.setName(name);
        user.setEmail(email);

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = bCryptPasswordEncoder.encode(password);
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    public User Login(String email, String password) throws UserNotFoundException {
        /*
        1. check if user with email exists
        2. if not exists, ask user to sign up
        3. if exists, check if the password matches
        4. if matches, return the user object
        5. if not matches, throw exception
         */
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        return user;
    }
}
