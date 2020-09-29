package ua.com.library.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.library.entity.User;
import ua.com.library.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    public Optional<User> findByLoginAndPassword(String login, String password){

        return userRepository.findByLoginAndPassword(login, password);
    }
}
