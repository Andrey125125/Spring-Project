package ua.com.library.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.library.entity.Author;
import ua.com.library.entity.Book;
import ua.com.library.repository.AuthorRepository;
import ua.com.library.repository.BookRepository;


import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BookService {

    BookRepository bookRepository;
    AuthorRepository authorRepository;


    public Page<Book> resolvePagesBookAdmin(int currentPage, int pageSize, String sortBy, String searchBy) {

        PageRequest sorted = PageRequest.of(currentPage - 1, pageSize, Sort.by(sortBy));
        if(searchBy.equals("")){
            return bookRepository.findAll( sorted);
        } else {
            return bookRepository.findAllByNameLike(searchBy, sorted);
        }


    }

    public Page<Book> resolvePagesBookReader(int currentPage, int pageSize, String sortBy, String searchBy) {

        PageRequest sorted = PageRequest.of(currentPage - 1, pageSize, Sort.by(sortBy));
        if(searchBy.equals("")){
            return bookRepository.findAllByQuantityGreaterThan(0, sorted);
        } else {
            return bookRepository.findAllByQuantityGreaterThanAndNameLike(0, searchBy, sorted);
        }


    }

    public Optional<List<Integer>> findPageNumbers(int quantity){

        //todo: refactor
        List<Integer> pages = null;
        if (quantity > 1) {
            pages = IntStream
                    .rangeClosed(1, quantity)
                    .boxed()
                    .collect(Collectors.toList());
        }

        return Optional.ofNullable(pages);

    }

    public Optional<Book> findBookById(Long id){
        return bookRepository.findById(id);
    }

    @Transactional
    public void changeQuantityById(long id, int quantity){
        Book book = bookRepository.getOne(id);
        book.setQuantity(quantity);
        bookRepository.save(book);

    }

    @Transactional
    public void addBook(String authorName, String bookName, String publisher, int amount){




        Author author = Author.builder().name(authorName).build();

        Optional<Author> temp = authorRepository.findByName(authorName);
        if ( ! temp.isPresent()){
            authorRepository.save(author);
        }




        List<Author> authors = new ArrayList<>();
        authors.add(temp.orElse(author));

        Book book = Book.builder()
                .authors(authors)
                .name(bookName)
                .publisher(publisher)
                .quantity(amount)
                .publishingDate(new Date(System.currentTimeMillis()))
                .build();

        bookRepository.save(book);

    }



}
