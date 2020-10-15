package ua.com.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.library.entity.Author;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByName(String name);

}
