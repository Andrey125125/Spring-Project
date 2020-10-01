package ua.com.library.service;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.com.library.entity.User;
import ua.com.library.repository.UserRepository;

import java.util.Objects;
import java.util.Optional;


@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder = passwordEncoder();

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    public Optional<User> findByLoginAndPassword(String login, String password){

        return userRepository.findByLoginAndPassword(login, password);
    }


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        if(Objects.isNull(login)) throw new UsernameNotFoundException("your username is empty");

        Optional<User> userOptional = userRepository.findByLogin(login);

        return userOptional.orElseThrow( () -> new UsernameNotFoundException("your username is empty"));


    }



    public User successfullyRegistered(User user) throws RuntimeException {

        if (userRepository.existsByLoginOrEmail(user.getLogin(), user.getEmail())){
            throw new RuntimeException("email or login is already taken");
        }

        user.setRole(User.Role.READER);

        user.setPassword(encoder.encode(user.getPassword()));

        userRepository.save(user);

        return user;

    }

}
