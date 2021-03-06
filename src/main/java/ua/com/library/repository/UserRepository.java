package ua.com.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.library.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String login);

    Optional<User> findByLoginAndPassword(String login, String password);

    Optional<User> findById(Long id);

    boolean existsByLoginOrEmail(String login, String password);
}
