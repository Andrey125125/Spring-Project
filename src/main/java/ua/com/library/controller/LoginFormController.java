package ua.com.library.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.com.library.dto.UserDTO;
import ua.com.library.entity.User;
import ua.com.library.service.UserService;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/")
public class LoginFormController {

    private final UserService userService;

    @Autowired
    public LoginFormController(UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    //@RequestMapping(value = "login", method = RequestMethod.POST)
    @PostMapping(value = "login")
    public void loginFormController(UserDTO user){
        Optional<User> user1 = userService.findByLoginAndPassword(user.getLogin(), user.getPassword());

        if (! user1.isPresent());

/*       userService.saveNewUser(User.builder()
                .firstName("Ann")
                .lastName("Reizer")
                .email("AnnReizer@testing.ua")
                .role(RoleType.ROLE_USER)
                .build());*/
    }

//    @RequestMapping(value = "user", method = RequestMethod.GET)
//    public UsersDTO getAllUser(){
//
//        return userService.getAllUsers();
//    }
}