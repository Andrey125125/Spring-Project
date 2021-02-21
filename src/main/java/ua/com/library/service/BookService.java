package ua.com.library.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.library.entity.Author;
import ua.com.library.entity.Book;
import ua.com.library.repository.AuthorRepository;
import ua.com.library.repository.BookRepository;


import java.util.*;
import java.sql.Date;
import java.util.stream.Collectors;
import java.util.stream.IntStream;




@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BookService {

    BookRepository bookRepository;
    AuthorRepository authorRepository;


    //todo: refactor ChangeQuantityById


    public Page<Book> resolvePagesBookAdmin(int currentPage, int pageSize, String sortBy, String searchBy) {

        PageRequest sorted = PageRequest.of(currentPage - 1, pageSize, Sort.by(sortBy));
        if(searchBy.equals("")){
            return bookRepository.findAll( sorted);
        } else {
            return bookRepository.findAllByNameLike(searchBy, sorted);
        }


    }

    public Page<Book> resolvePagesBookReader(int currentPage, int pageSize, String sortBy,  String searchBy) {

        PageRequest sorted = PageRequest.of(currentPage - 1, pageSize, Sort.by(sortBy));
        if(searchBy.equals("")){
            return bookRepository.findAllByQuantityGreaterThan(0, sorted);
        } else {
            return bookRepository.findAllByQuantityGreaterThanAndNameLike(0, searchBy, sorted);
        }


    }

    public Optional<List<Integer>> findPageNumbers(int quantity){

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


    public void changeQuantityById(long id, int quantity){
        bookRepository.updateQuantityById(quantity, id);

    }



    //todo: refactor this
    public void addBook(String authorsNamesString, String bookName, String publisher, int amount){

        List<Book> books = bookRepository.findAllByNameAndPublisher(bookName, publisher);
        HashSet<String> givenAuthorsNames = new HashSet<String> (Arrays.asList(authorsNamesString.split(",[\\s]*")));
        HashSet<String> authorsNames = new HashSet<>();

        for(Book book: books){
            for (Author author: book.getAuthors()) {
                authorsNames.add(author.getName());
            }
            if (authorsNames.equals(givenAuthorsNames)) throw new RuntimeException("book already exist!");

        }


        List<Author> authors = new ArrayList<>();
        for (String author: givenAuthorsNames){
            Optional<Author> optionalAuthor =  authorRepository.findByName(author);
            if (optionalAuthor.isPresent()) authors.add(optionalAuthor.get());
            else {
                Author author1 = Author.builder().name(author).build();
                authorRepository.save(author1);
                authors.add(author1);
            }
        }

        Book book = Book.builder()
                .authors(authors)
                .name(bookName)
                .publisher(publisher)
                .publishingDate(new Date(System.currentTimeMillis()))
                .quantity(amount)
                .build();
        bookRepository.save(book);



    }






}
