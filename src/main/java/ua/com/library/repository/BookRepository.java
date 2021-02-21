package ua.com.library.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.library.entity.Author;
import ua.com.library.entity.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {


    Page<Book> findAllByNameLike(String name, Pageable pageable);

    Page<Book> findAllByQuantityGreaterThan(int num, Pageable pageable);

    Page<Book> findAllByQuantityGreaterThanAndNameLike(int num, String name, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update Book b set b.quantity=:quantity where b.id=:id")
    void updateQuantityById(int quantity, long id);


    @Transactional
    @Modifying
    @Query("update Book b set b.quantity = (b.quantity + 1) where b.id =:id")  // : before parameter value
    void incrementQuantityById(long id);

    List<Book> findAllByNameAndPublisher( String name, String publisher);

}
